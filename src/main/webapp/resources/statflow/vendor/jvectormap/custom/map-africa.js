// Africa
$(function () {
  $('#mapAfrica').vectorMap({
    map: 'africa_mill',
    backgroundColor: 'transparent',
    scaleColors: ['#FF7E39'],
    zoomOnScroll: false,
    zoomMin: 1,
    hoverColor: true,
    series: {
      regions: [{
        values: gdpData,
        scale: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
        normalizeFunction: 'polynomial'
      }]
    },
  });
});