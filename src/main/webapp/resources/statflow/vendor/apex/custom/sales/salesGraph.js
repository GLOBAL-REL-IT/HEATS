var options = {
  chart: {
    height: 320,
    type: 'rangeBar',
    offsetY: 13,
    toolbar: {
      show: false,
    },
  },
  dataLabels: {
    enabled: false,
  },
  plotOptions: {
    bar: {
      horizontal: false,
      columnWidth: '20%',
      borderRadius: 8,
    },
  },
  stroke: {
    show: true,
    width: 6,
    colors: ['transparent']
  },
  series: [
    {
      data: [
        {
          x: 'Jan',
          y: [210, 400],
        },
        {
          x: 'Feb',
          y: [300, 490],
        },
        {
          x: 'Mar',
          y: [350, 500],
        },
        {
          x: 'Apr',
          y: [210, 390],
        },
        {
          x: 'May',
          y: [280, 400],
        },
        {
          x: 'Jun',
          y: [110, 250],
        },
        {
          x: 'Jul',
          y: [210, 400],
        },
        {
          x: 'Aug',
          y: [290, 390],
        },
        {
          x: 'Sep',
          y: [250, 490],
        },
        {
          x: 'Oct',
          y: [210, 390],
        },
        {
          x: 'Nov',
          y: [190, 310],
        },
        {
          x: 'Dec',
          y: [250, 450],
        },
      ],
    },
  ],
  grid: {
    borderColor: "#d8dee6",
    strokeDashArray: 5,
    xaxis: {
      lines: {
        show: true,
      },
    },
    yaxis: {
      lines: {
        show: false,
      },
    },
    padding: {
      top: 0,
      right: 0,
      bottom: 0,
      left: 0,
    },
  },
  xaxis: {
    categories: [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ],
  },
  yaxis: {
    labels: {
      show: false,
    },
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  markers: {
    size: 0,
    opacity: 0.3,
    colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
    strokeColor: "#ffffff",
    strokeWidth: 1,
    hover: {
      size: 7,
    },
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val;
      },
    },
    theme: 'dark'
  },
};

var chart = new ApexCharts(document.querySelector("#salesGraph"), options);
chart.render();