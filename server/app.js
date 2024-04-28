const express = require('express');
const puppeteer = require('puppeteer');
const helmet = require('helmet');
const compression = require('compression');

const app = express();

const getSchedule = async (url) => {
  try {
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
    const coursesData = await page.evaluate(() => {
      const coursesDivs = Array.from(document.querySelectorAll('.coursediv'));

      const coursesInfo = coursesDivs.map((course) => {
        const style = window.getComputedStyle(course);
        return {
          left: +style.getPropertyValue('left').split('p')[0],
          content: course.innerHTML.trim(),
          color: style.getPropertyValue('background-color'),
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

    const getDay = (coursePosition) => {
      const day = fillteredDays.filter((d) => d.left === coursePosition - 1);
      return day[0].content.split('/')[1];
    };
    const getTime = (time) => {
      if (isNaN(Number(time[0]))) return ['', ''];
      const startArr = time.split('-')[0].trim().split(':');
      const start = +startArr[0] * 60 + +startArr[1];
      const endArr = time.split('-')[1].trim().split(':');
      const end = +endArr[0] * 60 + +endArr[1];
      return {
        start,
        end,
      };
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
    const getNameType = (courseName) => {
      let name = courseName.split('">');
      let splitedName = '';
      if (name.length === 2) {
        splitedName = name[1].split(',');
      } else {
        splitedName = name[2].split(',');
      }

      let type = '';

      if (splitedName.length >= 2) {
        type = splitedName[splitedName.length - 1].trim();
        splitedName.pop();
        name = splitedName.join(',');
      } else name = splitedName[0];

      return {
        name,
        type,
      };
    };

    const coursesList = fillteredCourses.map((course) => {
      const time = getTime(course.content[course.content.length - 1]);
      const nameType = getNameType(course.content[0]);
      return {
        dayOfWeek: getDay(course.left),
        startTime: time.start,
        endTime: time.end,
        name: nameType.name,
        leader: getLeader(course),
        room: getRoom(course),
        type: nameType.type,
        color: course.color
          .split('(')[1]
          .slice(0, -1)
          .split(',')
          .map((n) => +n),
      };
    });
    coursesList.sort((a, b) => {
      if (a.start < b.start) return -1;
      if (a.start > b.start) return 1;
      return 0;
    });
    await browser.close();
    return coursesList;
  } catch (error) {
    throw new Error('Could not fetch schedule.');
  }
};

app.use(helmet());
app.use(compression());

app.use((req, res, next) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'OPTIONS, GET');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
  next();
});

app.get('/', async (req, res, next) => {
  try {
    const url = `${req.query.url}&id=${req.query.id}`;
    const courses = await getSchedule(url);
    res.json({ message: 'Courses fetched.', courses });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

app.listen(process.env.PORT || 3000);
