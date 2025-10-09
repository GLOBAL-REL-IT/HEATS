document.addEventListener('DOMContentLoaded', function () {
  var calendarEl = document.getElementById('dayGrid');

  // Get current date and year
  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth();

  // Format initial date to current month
  const initialDate = `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-12`;

  var calendar = new FullCalendar.Calendar(calendarEl, {
    headerToolbar: {
      left: 'prevYear,prev,next,nextYear today',
      center: 'title',
      right: 'dayGridMonth,dayGridWeek,dayGridDay'
    },
    initialDate: initialDate,
    navLinks: true, // can click day/week names to navigate views
    editable: true,
    dayMaxEvents: true, // allow "more" link when too many events
    events: [
      {
        title: 'All Day Event',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-01`,
        color: '#ecbe3d'
      },
      {
        title: 'Long Event',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-07`,
        end: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-10`,
        color: '#f17c55'
      },
      {
        groupId: 999,
        title: 'Birthday',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-09T16:00:00`,
        color: '#99a6f3'
      },
      {
        groupId: 999,
        title: 'Birthday',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-16T16:00:00`,
        color: '#ec4f3d'
      },
      {
        title: 'Conference',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-11`,
        end: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-13`,
        color: '#8eca77'
      },
      {
        title: 'Meeting',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-14T10:30:00`,
        end: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-14T12:30:00`
      },
      {
        title: 'Lunch',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-16T12:00:00`,
        color: '#f1a436'
      },
      {
        title: 'Meeting',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-18T14:30:00`,
        color: '#34c2d0'
      },
      {
        title: 'Interview',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-21T17:30:00`,
        color: '#B2D553'
      },
      {
        title: 'Meeting',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-22T20:00:00`,
        color: '#40cea6'
      },
      {
        title: 'Birthday',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-13T07:00:00`,
        color: '#f5678b'
      },
      {
        title: 'Click for Google',
        url: 'http://google.com/',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-28`,
        color: '#98c452'
      },
      {
        title: 'Interview',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-20`,
        color: '#a770b5'
      },
      {
        title: 'Product Launch',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-29`,
        color: '#f1a436'
      },
      {
        title: 'Leave',
        start: `${currentYear}-${(currentMonth + 1).toString().padStart(2, '0')}-25`,
        color: '#35b3c0'
      }
    ]
  });

  calendar.render();
});