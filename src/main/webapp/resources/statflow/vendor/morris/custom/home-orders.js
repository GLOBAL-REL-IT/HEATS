// Morris Donut
Morris.Donut({
  element: 'donutOrders',
  data: [
    { value: 15, label: 'New' },
    { value: 10, label: 'Delivered' },
    { value: 7, label: 'Pending' },
  ],
  backgroundColor: '#272b34',
  labelColor: '#272b34',
  lineWidth: '10px',
  colors: ["#507DFF", "#6A90FF", "#83A3FF", "#9DB6FF", "#B7C9FF", "#D0DCFF", "#EAEFFF"],
  resize: true,
  hideHover: "auto",
  formatter: function (x) { return x }
});