var options = {
  chart: {
    width: 300,
    type: 'donut',
  },
  labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
  series: [20, 20, 20, 20, 20],
  legend: {
    position: 'bottom',
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    width: 0,
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
}
var chart = new ApexCharts(
  document.querySelector("#basic-donut-graph"),
  options
);
chart.render();