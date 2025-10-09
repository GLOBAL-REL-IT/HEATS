var options = {
  series: [70],
  chart: {
    height: 310,
    type: 'radialBar',
    offsetY: 0,
  },

  stroke: {
    dashArray: 25,
    curve: 'smooth',
    lineCap: 'round',
  },
  grid: {
    padding: {
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
    },
  },
  plotOptions: {
    radialBar: {
      startAngle: -135,
      endAngle: 135,
      hollow: {
        size: '75%',
        image: 'assets/images/ellipse-bg.svg',
        imageWidth: 140,
        imageHeight: 140,
        imageClipped: false,
      },
      track: {
        show: true,
        background: 'rgba(0, 0, 0, 0.1)',
        strokeWidth: '97%',
        opacity: 0.4,
      },
      dataLabels: {
        show: true,
        name: {
          show: true,
          fontSize: '15px',
          fontFamily: undefined,
          fontWeight: 700,
          color: undefined,
          offsetY: -10,
        },
        value: {
          show: true,
          colors: '#507DFF',
          fontSize: '21px',
          fontWeight: 700,
          offsetY: 6,
          formatter: function (val) {
            return val + '%';
          },
        },
      },
    },
  },
  labels: ['Today: 90', 'Yesterday: 60'],
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  legend: {
    show: true,
    position: 'bottom',
    fontSize: '14px',
    fontWeight: 500,
    markers: {
      width: 18,
      height: 18,
      strokeWidth: 5,
    },
    onItemClick: {
      toggleDataSeries: false,
    },
    onItemHover: {
      highlightDataSeries: false,
    },
  },
};

var chart = new ApexCharts(document.querySelector("#conversions"), options);
chart.render();