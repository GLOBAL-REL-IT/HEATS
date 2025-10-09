var options = {
  chart: {
    height: 280,
    type: 'bar',
    toolbar: {
      show: false,
    },
  },
  plotOptions: {
    bar: {
      horizontal: false,
      columnWidth: '20%',
      distributed: true,
      borderRadius: 10,
    },
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    show: true,
    width: 0,
    colors: ['rgba(62, 68, 81, .3)']
  },
  series: [{
    name: 'Sales',
    data: [52, 73, 34, 66, 82, 49]
  }],
  legend: {
    show: false,
  },
  xaxis: {
    categories: ["usa", "ind", "brz", "tur", "ger", "rus"],
  },
  yaxis: {
    show: false,
  },
  fill: {
    colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return + val
      }
    }
  },
  grid: {
    show: false,
    xaxis: {
      lines: {
        show: true
      }
    },
    yaxis: {
      lines: {
        show: false,
      }
    },
    padding: {
      top: 0,
      right: 0,
      bottom: -10,
      left: 0
    },
  },
  colors: ['#ffffff'],
}
var chart = new ApexCharts(
  document.querySelector("#byCountry"),
  options
);
chart.render();
