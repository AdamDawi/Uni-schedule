const express = require('express');
const puppeteer = require('puppeteer');

const app = express();

const getSchedule = async (url) => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto(url);
  await page.waitForNavigation();

  const daysData = await page.evaluate(() => {
    const cdDivs = Array.from(document.querySelectorAll('.cd'));

    const daysInfo = cdDivs.map((cd) => {
      const style = window.getComputedStyle(cd);
      return {
        left: +style.getPropertyValue('left').split('p')[0],
        content: cd.textContent,
      };
    });

    return daysInfo;
  });

  const fillteredDays = daysData.filter(
    (day) =>
      day.content === 'Poniedziałek/Monday' ||
      day.content === 'Wtorek/Tuesday' ||
      day.content === 'Środa/Wednesday' ||
      day.content === 'Czwartek/Thursday' ||
      day.content === 'Piątek/Friday' ||
      day.content === 'Sobota/Saturday' ||
      day.content === 'Niedziela/Sunday'
  );
  console.log(fillteredDays);
  const coursesData = await page.evaluate(() => {
    const coursesDivs = Array.from(document.querySelectorAll('.coursediv'));

    const coursesInfo = coursesDivs.map((course) => {
      const style = window.getComputedStyle(course);
      return {
        left: +style.getPropertyValue('left').split('p')[0],
        content: course.innerHTML.trim(),
      };
    });
    return coursesInfo;
  });

  let fillteredCourses = coursesData.filter(
    (course) => course.content[0] === '<'
  );

  fillteredCourses = fillteredCourses.map((course) => {
    return { ...course, content: course.content.trim().split('<br>') };
  });

  console.log(fillteredCourses);

  const getDay = (coursePosition) => {
    const day = fillteredDays.filter((d) => d.left === coursePosition - 1);
    return day[0].content.split('/')[1];
  };
  const getTime = (time) => {
    if (isNaN(Number(time[0]))) return ['', ''];
    return [time.split('-')[0].trim(), time.split('-')[1].trim()];
  };
  const getName = (stringName) => {
    const name = stringName.split('">');
    if (name.length === 2) return name[1];
    return name[2];
  };
  const getLeader = (course) => {
    const leaderString = course.content.filter((s) => s.includes('type=10'));
    if (!leaderString.length) return '';
    return leaderString[0].split('">')[1].slice(0, -4);
  };
  const getRoom = (course) => {
    const roomString = course.content.filter((s) => s.includes('type=20'));
    if (!roomString.length) return '';
    return roomString[0].split('">')[1].slice(0, -4);
  };

  const coursesList = fillteredCourses.map((course) => {
    const time = getTime(course.content[course.content.length - 1]);
    return {
      day: getDay(course.left),
      start: time[0],
      end: time[1],
      name: getName(course.content[0]),
      leader: getLeader(course),
      room: getRoom(course),
    };
  });
  await browser.close();
  return coursesList;
};

app.get('/', async (req, res, next) => {
  const url = `${req.query.url}&id=${req.query.id}`;
  const courses = await getSchedule(url);
  res.json({ message: 'Courses fetched.', courses });
});

app.listen(3000);
