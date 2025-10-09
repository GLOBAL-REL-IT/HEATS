var options = {
  chart: {
    height: 280,
    type: 'area',
    toolbar: {
      show: false,
    },
    fontFamily: 'Nunito, sans-serif',
    dropShadow: {
      enabled: true,
      opacity: 0.1,
      blur: 5,
      left: -7,
      top: 22
    },
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    show: true,
    curve: 'smooth',
    width: 3,
    lineCap: 'round',
  },
  series: [{
    name: 'Claimed',
    data: [300, 400, 600, 500, 700, 500, 600]
  }, {
    name: 'Expired',
    data: [100, 200, 400, 300, 500, 400, 300]
  }],
  fill: {
    type: 'gradient',
    gradient: {
      shadeIntensity: 1,
      opacityFrom: 0.7,
      opacityTo: 0.2,
      stops: [0, 90, 100]
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
        show: false,
      }
    },
    padding: {
      top: 0,
      right: 30,
      bottom: 0,
      left: 30
    },
  },
  xaxis: {
    type: 'category',
    categories: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    labels: {
      style: {
        fontSize: '12px',
        fontWeight: 500,
      },
    },
    axisBorder: {
      show: false
    },
    axisTicks: {
      show: false
    }
  },
  colors: ["#507DFF", "#FF6B8A"],
  yaxis: {
    show: false,
    min: 0,
    max: 800,
    tickAmount: 4
  },
  markers: {
    size: 0,
    opacity: 0.2,
    strokeColor: "#fff",
    strokeWidth: 2,
    hover: {
      size: 7,
    }
  },
  tooltip: {
    x: {
      format: 'dd/MM/yy'
    },
    y: {
      formatter: function (value) {
        return value + " deals"
      }
    },
    theme: 'dark',
    fillSeriesColor: false
  },
  legend: {
    position: 'top',
    horizontalAlign: 'right',
    offsetY: -20,
    fontSize: '13px',
    markers: {
      radius: 12
    }
  }
}

var chart = new ApexCharts(
  document.querySelector("#deals"),
  options
);

chart.render();