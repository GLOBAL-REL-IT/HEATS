// Loading
$(function () {
  $("#loading-wrapper").fadeOut(1000);
});

// Toggle sidebar
$("#toggle-sidebar").on('click', function () {
  $(".page-wrapper").toggleClass("toggled");
});

// Sidebars JS
jQuery(function ($) {

  $(".sidebar-dropdown > a").on('click', function () {
    $(".sidebar-submenu").slideUp(300);
    if ($(this).parent().hasClass("active")) {
      $(".sidebar-dropdown").removeClass("active");
      $(this).parent().removeClass("active");
    } else {
      $(".sidebar-dropdown").removeClass("active");
      $(this).next(".sidebar-submenu").slideDown(300);
      $(this).parent().addClass("active");
    }
  });

  // Added by Srinu 
  $(function () {
    // When the window is resized
    $(window).resize(function () {
      // Check window width and apply appropriate classes
      if ($(window).width() <= 768) {
        $(".page-wrapper").removeClass("pinned");
      } else {
        $(".page-wrapper").removeClass("toggled");
      }
    });
  });

});


// Toggle graph day selection
$(function () {
  $(".day-toggle .btn").on('click', function () {
    $(".day-toggle .btn").removeClass("btn-dark");
    $(this).addClass("btn-dark");
  });
});


// Countdown
// Countdown
$(function () {
  function initCountdown() {
    const countDownDate = new Date().getTime() + 24 * 60 * 60 * 1000;
    const countdownTimer = setInterval(function () {
      const now = new Date().getTime();
      const timeRemaining = countDownDate - now;
      const hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

      if (document.getElementById("countdown")) {
        document.getElementById("countdown").innerHTML =
          (hours < 10 ? "0" + hours : hours) + ":" +
          (minutes < 10 ? "0" + minutes : minutes) + ":" +
          (seconds < 10 ? "0" + seconds : seconds);

        if (timeRemaining < 0) {
          clearInterval(countdownTimer);
          document.getElementById("countdown").innerHTML = "EXPIRED";
        }
      }
    }, 1000);
  }

  if (document.getElementById("countdown")) {
    initCountdown();
  }
});


// Buy Sell Table
$(function () {
  'use strict';
  if ($('.buy-sell-table').length) {
    setRandomClass();
    setInterval(function () {
      setRandomClass();
    }, 1000);
    function setRandomClass() {
      var tbody = $(".buy-sell-table tbody");
      var items = tbody.find("tr td");
      var number = items.length;
      var random1 = Math.floor((Math.random() * number));
      var random2 = Math.floor((Math.random() * number));
      var random3 = Math.floor((Math.random() * number));
      var random4 = Math.floor((Math.random() * number));
      var random5 = Math.floor((Math.random() * number));
      items.removeClass("text-danger text-success");
      items.eq(random1).addClass("text-danger");
      items.eq(random2).addClass("text-success");
      items.eq(random3).addClass("text-danger");
      items.eq(random4).addClass("text-success");
      items.eq(random5).addClass("text-danger");
    }
  }
});

// Price Highlight Random
$(function () {
  'use strict';
  if ($('.price-highlight-random').length) {
    setRandomClass();
    setInterval(function () {
      setRandomClass();
    }, 2000);
    function setRandomClass() {
      var highlight = $(".price-highlight-random");
      var items = highlight.find("h6");
      var number = items.length;
      var random1 = Math.floor((Math.random() * number));
      var random2 = Math.floor((Math.random() * number));
      items.removeClass("text-danger text-success");
      items.eq(random1).addClass("text-danger");
      items.eq(random2).addClass("text-success");
    }
  }
});

// Column Highlight Random
$(function () {
  'use strict';
  if ($('.column-highlight').length) {
    setRandomClass();
    setInterval(function () {
      setRandomClass();
    }, 1000);
    function setRandomClass() {
      var tbody = $(".column-highlight tbody");
      var items = tbody.find("tr td:nth-child(3)");
      var number = items.length;
      var random1 = Math.floor((Math.random() * number));
      var random2 = Math.floor((Math.random() * number));
      var random3 = Math.floor((Math.random() * number));
      items.removeClass("text-danger text-success");
      items.eq(random1).addClass("text-danger");
      items.eq(random2).addClass("text-success");
      items.eq(random3).addClass("text-danger");
    }
  }
});

// Todays Date
$(function () {
  var interval = setInterval(function () {
    var momentNow = moment();
    $('.todaysDate').html(momentNow.format('LLLL'));
  }, 100);
});


/***********
***********
***********
  Bootstrap JS 
***********
***********
***********/

// Tooltip
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})

// Popover
var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
  return new bootstrap.Popover(popoverTriggerEl)
})