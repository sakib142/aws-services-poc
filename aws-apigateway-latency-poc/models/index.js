
let datestamp = Date.now();
let users = {
  1: {
    id: '1',
    time: Date.now(),
    username: 'Robin Wieruch',
  },
  2: {
    id: '2',
    time: datestamp+1,
    username: 'Dave Davids',
  },
};

let messages = {
  1: {
    id: '1',
    text: 'Hello World',
    userId: '1',
  },
  2: {
    id: '2',
    text: 'By World',
    userId: '2',
  },
};

module.exports =  {
  users,
  messages,
};
