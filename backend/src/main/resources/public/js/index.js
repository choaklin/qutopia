/**
 * Created by choaklin on 2017/3/31.
 */
function fadeOut (ele, speed) {
    var opacity = 1;
    var eachAdd = 1/speed
    var timer = setInterval(function () {
        opacity -= eachAdd;
        ele.style.opacity = opacity;
        if (opacity < 0) {
            ele.style.opacity = 0;
            clearInterval(timer);
            ele.style.display = 'none'
        };
    },speed);
}

function fadeIn (ele, speed) {
    var opacity = 0;
    var eachAdd = 1/speed;

    // 先展示，但是透明要设置为0，即不可见
    ele.style.display = 'block'
    ele.style.opacity = 0;
    var timer = setInterval(function () {
        opacity += eachAdd;
        ele.style.opacity = opacity;
        if (opacity > 1) {
            ele.style.opacity = 1;
            clearInterval(timer);
        };
    },speed);
}

var init = {
    waite: function() {
        var c1 = document.getElementById('welcome');
        var ctx1 = c1.getContext('2d');

        var welcomeRotate = {
            w: 250,
            h: 250,
            count: 85,
            rotation: 0,
            speed: 0.06,
            step: function () {
                welcomeRotate.rotation += welcomeRotate.speed;
            },
            draw: function () {
                ctx1.globalCompositeOperation = 'source-over';
                ctx1.save();
                ctx1.translate(125, 125);
                ctx1.rotate(welcomeRotate.rotation);
                var i = welcomeRotate.count;
                while( i-- ) {
                    ctx1.beginPath();
                    ctx1.arc(0, 0, i+(Math.random()*35), Math.random(), Math.PI/3+(Math.random()/12));
                    ctx1.stroke();
                }
                ctx1.restore();
            },
            loop: function () {
                requestAnimationFrame(welcomeRotate.loop);
                ctx1.globalCompositeOperation = 'destination-out';
                ctx1.fillStyle = 'rgba(0, 0, 0, 0.03)';
                ctx1.fillRect( 0, 0, welcomeRotate.w, welcomeRotate.h );
                welcomeRotate.step();
                welcomeRotate.draw();
            }
        };
        c1.width = welcomeRotate.w;
        c1.height  = welcomeRotate.h;
        ctx1.strokeStyle = 'rgba(0, 0, 0, 0.75)';
        ctx1.lineWidth = 0.5;
        welcomeRotate.loop();
    },

    hideWelcome: function () {
        var welcome = document.getElementById('welcome');
        var timer = setTimeout(function () {
            fadeOut(welcome, 50)
        }, 1000)
    },
	
	showMain: function () {
		var main = document.getElementById('home');
		var timer = setTimeout(function () {
			fadeIn(main, 30)
		}, 4000)
	}
};
