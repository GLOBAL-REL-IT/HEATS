var options = {
  chart: {
    height: 380,
    type: 'bar',
    stacked: true,
    toolbar: {
      show: false
    },
    zoom: {
      enabled: true
    }
  },
  plotOptions: {
    bar: {
      horizontal: false,
      columnWidth: '30%',
      distributed: true,
      borderRadius: 10,
    }
  },
  dataLabels: {
    enabled: true
  },
  stroke: {
    show: true,
    width: 0,
    colors: ['rgba(62, 68, 81, .3)']
  },
  series: [{
    name: 'Orders',
    data: [10, 15, 25, 35, 45, 55, 65]
  }, {
    name: 'Revenue',
    data: [15, 20, 30, 40, 50, 60, 70]
  }],
  xaxis: {
    categories: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
  },
  legend: {
    position: 'bottom',
    offsetY: 0,
  },
  grid: {
    borderColor: "#d8dee6",
    strokeDashArray: 5,
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
      bottom: 10,
      left: 10
    },
  },
  yaxis: {
    show: false,
  },
  fill: {
    opacity: 1
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return "$" + val + " thousands"
      }
    }
  },
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
}
var chart = new ApexCharts(
  document.querySelector("#orders"),
  options
);
chart.render();

