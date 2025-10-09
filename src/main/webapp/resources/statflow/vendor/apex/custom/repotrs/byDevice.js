var options = {
  chart: {
    width: 360,
    type: "donut",
  },
  labels: ['Desktop', 'Laptop', 'Tablet', 'Mobile'],
  series: [40000, 30000, 40000, 15000],
  legend: {
    position: "bottom",
  },
  dataLabels: {
    enabled: false,
  },
  stroke: {
    width: '2',
    colors: ['#ffffff'],
  },
  fill: {
    type: 'gradient',
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
};
var chart = new ApexCharts(document.querySelector("#byDevice"), options);
chart.render();