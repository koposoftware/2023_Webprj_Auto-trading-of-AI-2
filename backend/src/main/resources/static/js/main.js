(function ($) {
    "use strict";

    let spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();
    

    new WOW().init();
    
    const $dropdown = $(".dropdown");
    const $dropdownToggle = $(".dropdown-toggle");
    const $dropdownMenu = $(".dropdown-menu");
    const showClass = "show";
    
    $(window).on("load resize", function() {
        if (this.matchMedia("(min-width: 992px)").matches) {
            $dropdown.hover(
            function() {
                const $this = $(this);
                $this.addClass(showClass);
                $this.find($dropdownToggle).attr("aria-expanded", "true");
                $this.find($dropdownMenu).addClass(showClass);
            },
            function() {
                const $this = $(this);
                $this.removeClass(showClass);
                $this.find($dropdownToggle).attr("aria-expanded", "false");
                $this.find($dropdownMenu).removeClass(showClass);
            }
            );
        } else {
            $dropdown.off("mouseenter mouseleave");
        }
    });
    

    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    $('[data-toggle="counter-up"]').counterUp({
        delay: 10,
        time: 2000
    });


    $(document).ready(function () {
        let $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })
    });

    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        margin: 25,
        dots: false,
        loop: true,
        nav : true,
        navText : [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsive: {
            0:{
                items:1
            },
            768:{
                items:2
            }
        }
    });
    
})(jQuery);


document.addEventListener("DOMContentLoaded", function() {
    let form = document.querySelector("form");
    let emailInput = document.getElementById("email");
    let passwordInput = document.getElementById("password");
    let loginAttempts = 0;
    let loginBlocked = false;

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        if (loginBlocked) {
            alert("로그인이 일시적으로 제한되었습니다. 5분 후에 다시 시도해주세요.");
            return;
        }

        let email = emailInput.value;
        let password = passwordInput.value;

        if (email === "" || password === "") {
            let errorDiv = document.createElement("div");
            errorDiv.className = "alert alert-danger";

            if (email === "") {
                errorDiv.innerText = "이메일을 입력해주세요";
            } else {
                errorDiv.innerText = "비밀번호를 입력해주세요";
            }

            let errorContainer = document.getElementById("error-container");
            errorContainer.innerHTML = ""; 
            errorContainer.appendChild(errorDiv);

            return; 
        }

        fetch("./json/user.json")
            .then(function(response) {
                return response.json();
            })
            .then(function(data) {
                let users = data.users;
                let state = false;

                for (let i = 0; i < users.length; i++) {
                    if (users[i].email === email && users[i].password === password) {
                        state = true;
                        break;
                    }
                }

                if (state) {
                    alert("로그인 성공");
                    window.location.href = "index.html";
                } else {
                    loginAttempts++;

                    if (loginAttempts >= 5) {
                        loginBlocked = true;
                        setTimeout(function() {
                            loginAttempts = 0;
                            loginBlocked = false;
                        }, 300000); 

                        alert("로그인 실패. 로그인이 일시적으로 제한됩니다. 5분 후에 다시 시도해주세요.");
                    } else {
                        alert("로그인 실패");
                    }
                }
            })
            .catch(function(error) {
                console.error("JSON 파일 에러", error);
            });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    let password = document.getElementById("register-password");
    let confirmPassword = document.getElementById("confirm-password");
    let errorContainer = document.getElementById("error-container");

    confirmPassword.addEventListener("keyup", function () {
        if (password.value !== confirmPassword.value) {
            errorContainer.innerText = "비밀번호가 일치하지 않습니다.";
        } else {
            errorContainer.innerText = "";
        }
    });

    let specialChars = /[!@#$%^&*(),.?":{}|<>]/;

    password.addEventListener("keyup", function () {
        let passwordValue = password.value;
        let containsSpecialChars = specialChars.test(passwordValue);
        let errorMessage = "";

        if (!containsSpecialChars) {
            errorMessage += "비밀번호는 특수문자를 포함해야 합니다.\n";
        }

        if (errorMessage !== "") {
            errorContainer.innerText = errorMessage;
        } else {
            errorContainer.innerText = "";
        }
    });

    let form = document.querySelector("form");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        let successMessage = document.createElement("div");
        successMessage.className = "alert alert-success";
        successMessage.innerText = "회원가입 성공";

        let container = document.querySelector(".container");
        container.appendChild(successMessage);

        setTimeout(function () {
            window.location.href = "index.html";
        }); 
    });
});



document.addEventListener("DOMContentLoaded", function () {
    fetch("./json/cards.json")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            let cards = data.cards;
            let cardContainer = document.getElementById("card-container");

            cards.forEach(function (card) {
                let cardItem = document.createElement("div");
                cardItem.className = "col-lg-4 col-md-6 wow fadeInUp";
                cardItem.setAttribute("data-wow-delay", "0.2s");

                let cardLink = document.createElement("a");
                cardLink.className = "service-item rounded";
                cardLink.href = "";

                let cardImage = document.createElement("img");
                cardImage.src = card.image;
                cardImage.alt = "";

                let cardDescription = document.createElement("p");
                cardDescription.className = "text-body mb-0";
                cardDescription.innerText = card.description;

                cardLink.appendChild(cardImage);
                cardLink.appendChild(document.createElement("br"));
                cardLink.appendChild(cardDescription);

                cardItem.appendChild(cardLink);
                cardContainer.appendChild(cardItem);
            });
        })
        .catch(function (error) {
            console.error("JSON 파일 에러", error);
        });
});

document.addEventListener("DOMContentLoaded", function () {
    fetch("./json/trip.json")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            let trips = data.trips;
            let tripContainer = document.getElementById("trip-container");

            trips.forEach(function (trip) {
                let tripItem = document.createElement("div");
                tripItem.className = "col-lg-4 col-md-6 wow fadeInUp";
                tripItem.setAttribute("data-wow-delay", "0.1s");

                let roomItem = document.createElement("div");
                roomItem.className = "room-item shadow rounded overflow-hidden";

                let positionRelative = document.createElement("div");
                positionRelative.className = "position-relative";

                let img = document.createElement("img");
                img.className = "img-fluid";
                img.style.width = "408px";
                img.style.height = "245px";
                img.src = trip.image;
                img.alt = "";

                let priceTag = document.createElement("small");
                priceTag.className = "position-absolute start-0 top-100 translate-middle-y bg-primary text-white rounded py-1 px-3 ms-4";
                priceTag.innerText = trip.price;

                positionRelative.appendChild(img);
                positionRelative.appendChild(priceTag);

                let p4 = document.createElement("div");
                p4.className = "p-4 mt-2";

                let dFlex = document.createElement("div");
                dFlex.className = "d-flex justify-content-between mb-3";

                let title = document.createElement("h5");
                title.className = "mb-0";
                title.innerText = trip.title;

                let stars = document.createElement("div");
                stars.className = "ps-2";

                for (let i = 0; i < trip.stars; i++) {
                    let star = document.createElement("small");
                    star.className = "fa fa-star text-primary";
                    stars.appendChild(star);
                }

                dFlex.appendChild(title);
                dFlex.appendChild(stars);

                let description = document.createElement("p");
                description.className = "text-body mb-3";
                description.innerText = trip.description;

                let buttonGroup = document.createElement("div");
                buttonGroup.className = "d-flex justify-content-between";

                let detailButton = document.createElement("a");
                detailButton.className = "btn btn-sm btn-primary rounded py-2 px-4";
                detailButton.href = trip.detailUrl;
                detailButton.innerText = "상세정보";

                let reservationButton = document.createElement("a");
                reservationButton.className = "btn btn-sm btn-dark rounded py-2 px-4";
                reservationButton.href = trip.reservationUrl;
                reservationButton.innerText = "예약";

                buttonGroup.appendChild(detailButton);
                buttonGroup.appendChild(reservationButton);

                p4.appendChild(dFlex);
                p4.appendChild(description);
                p4.appendChild(buttonGroup);

                roomItem.appendChild(positionRelative);
                roomItem.appendChild(p4);

                tripItem.appendChild(roomItem);
                tripContainer.appendChild(tripItem);
            });
        })
        .catch(function (error) {
            console.error("JSON 파일 에러", error);
        });
});


function initializeWow() {
    new WOW().init();
}

const recommendationBtn = document.getElementById("recommendationBtn");
const travelBtn = document.getElementById("travelBtn");
const exchangeBtn = document.getElementById("exchangeBtn");
const cardSection = document.getElementById("cardSection");
const travelSection = document.getElementById("travelSection");
const exchangeSection = document.getElementById("exchangeSection");


recommendationBtn.addEventListener("click", () => {
    cardSection.style.display = "block";
    travelSection.style.display = "none";
    exchangeSection.style.display = "none";
    initializeWow();
});

travelBtn.addEventListener("click", () => {
    cardSection.style.display = "none";
    travelSection.style.display = "block";
    exchangeSection.style.display = "none";
    initializeWow();
});

exchangeBtn.addEventListener("click", () => {
    cardSection.style.display = "none";
    travelSection.style.display = "none";
    exchangeSection.style.display = "block";
    initializeWow();
});

window.onload = function() {
    const listItems = document.querySelectorAll('.stock-wrap li');

    let counter = 0;

    function showNextItem() {
        if (listItems[counter]) {
            listItems[counter].classList.remove('visible');
        }
        counter = (counter + 1) % listItems.length;
        listItems[counter].classList.add('visible');
        setTimeout(showNextItem, 5000);
    }

    // Start the cycle
    showNextItem();
}

var ctx = document.getElementById('chart').getContext('2d');
    var chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            datasets: [{
                label: 'Sample Data',
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgb(255, 99, 132)',
                data: [0, 10, 5, 2, 20, 30, 45]
            }]
        }
    });


