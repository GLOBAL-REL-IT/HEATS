var options = {
  chart: {
    height: 300,
    type: 'bar',
    toolbar: {
      show: false,
    },
  },
  plotOptions: {
    bar: {
      horizontal: true,
      barHeight: '40%',
      borderRadius: 4,
      distributed: true,
    }
  },
  dataLabels: {
    enabled: false
  },
  series: [{
    data: [400, 430, 448, 470, 540, 1200, 1380]
  }],
  xaxis: {
    categories: ['Canada', 'Netherlands', 'Italy', 'France', 'Japan', 'USA', 'India'],
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
      bottom: 0,
      left: 0
    },
  },
  colors: ["#4361ee", "#3f78e0", "#2b2e4a", "#5d87ff", "#00b8d9", "#0cbc87", "#fa5c7c"],
  theme: {
    mode: 'light',
  },
  responsive: [{
    breakpoint: 480,
    options: {
      chart: {
        height: 250
      }
    }
  }]
}

var chart = new ApexCharts(
  document.querySelector("#basic-bar-graph"),
  options
);

chart.render();