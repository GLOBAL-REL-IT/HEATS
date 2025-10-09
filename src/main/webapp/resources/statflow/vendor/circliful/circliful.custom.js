$(document).ready(function () {

  $("#overallSales").circliful({
    animation: 1,
    animationStep: 5,
    foregroundBorderWidth: 25,
    backgroundBorderWidth: 25,
    percent: 90,
    fontColor: '#ffffff',
    foregroundColor: '#ffffffs',
    backgroundColor: '#363a45',
    multiPercentage: 1,
    percentages: [10, 20, 30]
  });

  $("#overallExpenses").circliful({
    animation: 1,
    animationStep: 5,
    foregroundBorderWidth: 25,
    backgroundBorderWidth: 25,
    percent: 80,
    fontColor: '#afb7c9',
    foregroundColor: '#57c012',
    backgroundColor: '#363a45',
    multiPercentage: 1,
    percentages: [10, 20, 30]
  });

  $("#overallIncome").circliful({
    animation: 1,
    animationStep: 5,
    foregroundBorderWidth: 25,
    backgroundBorderWidth: 25,
    percent: 70,
    fontColor: '#afb7c9',
    foregroundColor: '#ac5cdd',
    backgroundColor: '#363a45',
    multiPercentage: 1,
    percentages: [10, 20, 30]
  });


});