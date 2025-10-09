var series = {
  "monthDataSeries1": {
    "prices": [
      9107, 9128, 9122, 9165, 9340, 8423, 8423, 8514, 8481, 8487, 8506, 8881, 9340
    ],
    "dates": [
      "13 Nov 2023", "14 Nov 2023", "15 Nov 2023", "16 Nov 2023", "17 Nov 2023",
      "20 Nov 2023", "21 Nov 2023", "22 Nov 2023", "23 Nov 2023", "24 Nov 2023",
      "27 Nov 2023", "28 Nov 2023", "29 Nov 2023"
    ]
  }
}

var options = {
  chart: {
    height: 300,
    type: 'area',
    zoom: {
      enabled: false
    },
    toolbar: {
      show: false,
    },
    dropShadow: {
      enabled: true,
      opacity: 0.2,
      blur: 5,
      left: -7,
      top: 7
    },
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'smooth',
    width: 3,
  },
  series: [{
    name: "Visitors",
    data: series.monthDataSeries1.prices
  }],
  title: {
    text: 'Daily Visitors',
    align: 'center',
    style: {
      fontSize: '16px',
      fontWeight: 600
    }
  },
  grid: {
    borderColor: "#e0e6ed",
    strokeDashArray: 5,
    xaxis: {
      lines: {
        show: true
      }
    },
    yaxis: {
      lines: {
        show: true,
      }
    },
    padding: {
      top: 0,
      right: 10,
      bottom: 0,
      left: 10
    },
  },
  labels: series.monthDataSeries1.dates,
  xaxis: {
    type: 'datetime',
    labels: {
      style: {
        fontSize: '12px'
      }
    }
  },
  yaxis: {
    opposite: true,
    labels: {
      style: {
        fontSize: '12px'
      }
    }
  },
  legend: {
    horizontalAlign: 'left',
    offsetX: 10
  },
  colors: ['#4361ee'],
  fill: {
    type: 'gradient',
    gradient: {
      shadeIntensity: 1,
      opacityFrom: 0.7,
      opacityTo: 0.2,
      stops: [0, 90, 100]
    }
  },
  markers: {
    size: 4,
    opacity: 0.9,
    colors: ['#4361ee'],
    strokeColor: "#fff",
    strokeWidth: 2,
    hover: {
      size: 7,
    }
  },
  tooltip: {
    theme: 'dark',
    y: {
      formatter: function (val) {
        return val + " visitors"
      }
    }
  }
}

var chart = new ApexCharts(
  document.querySelector("#basic-area-graph"),
  options
);

chart.render();