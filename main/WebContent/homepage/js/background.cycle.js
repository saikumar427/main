var currentImageIndex = 0;
var imageIds = new Array();
var fadeSpeed;
var interval;
var isPaused = false;
//Sizing constants. these determine the value of the CSS property 'background-size' of the selected container
var SCALING_MODE_NONE = 0; //Uses the original image size
var SCALING_MODE_STRETCH = 1; //Sets 'background-size' to '100% 100%'. This stretches the background image to fill the container, discarding the images aspect ratio.
var SCALING_MODE_COVER = 2; //Sets 'background-size' to 'cover'. This makes the background images fill the entire container while retaining its aspect ratio.
var SCALING_MODE_CONTAIN = 3; //Sets 'background-size' to 'contain'. This scales the bakcground image to the largest size such that both its width and its height can fit inside the content area

$.fn.backgroundCycle = function(options) {
    var settings = $.extend({
        imageUrls: [],
        duration: 8000,
        fadeSpeed: 1000,
        backgroundSize: SCALING_MODE_NONE
    }, options);

    fadeSpeed = settings.fadeSpeed;

    var marginTop = this.css('margin-top');
    var marginRight = this.css('margin-right');
    var marginBottom = this.css('margin-bottom');
    var marginLeft = this.css('margin-left');

    if (!this.is("body")) {
        this.css({
            position: 'relative'
        });
    }

    var contents = $(document.createElement('div'));

    var children = this.children().detach();
    contents.append(children);

    imageIds = new Array();

    for (var i = 0; i < settings.imageUrls.length; i++) {
        var id = 'bgImage' + i;
		
        var src = settings.imageUrls[i];
        var cssClass = 'cycle-bg-image';

        var image = $(document.createElement('div'));
        image.attr('id', id);
        image.attr('class', cssClass);

        var sizeMode;

        switch (settings.backgroundSize) {
            default:
                sizeMode = settings.backgroundSize;
                break;
            case SCALING_MODE_NONE:
                sizeMode = 'auto';
                break;
            case SCALING_MODE_STRETCH:
                sizeMode = '100% 100%';
                break;
            case SCALING_MODE_COVER:
                sizeMode = 'cover';
                break;
            case SCALING_MODE_CONTAIN:
                sizeMode = 'contain';
                break;
        }

        image.css({
            'background-image': "url('" + src + "')",
            'background-repeat': 'no-repeat',
            'background-size': sizeMode,
            '-moz-background-size': sizeMode,
            '-webkit-background-size': sizeMode,
            position: 'absolute',
            left: marginLeft,
            top: marginTop,
            right: marginRight,
            bottom: marginBottom
        });

        this.append(image);
        imageIds.push(id);
    }
    contents.css({
        position: 'absolute',
        left: marginLeft,
        top: marginTop,
        right: marginRight,
        bottom: marginBottom
    });
    this.append(contents);
    $('.cycle-bg-image').hide();
    $('#' + imageIds[Math.floor((Math.random() * 15) + 0)]).show();
    interval = setInterval(cycleToNextImage, 8000);
	$( "#emailid" ).keyup(function() {
	isPaused = true;
	});
	$( "#beeid" ).keyup(function() {
	isPaused = true;
	});
	$( "#pwdid" ).keyup(function() {
	isPaused = true;
	});	
};

function cycleToNextImage() {
	//alert(currentImageIndex);
	//alert(isPaused);
	if(!isPaused) {
	index=currentImageIndex;
    var previousImageId = imageIds[currentImageIndex];

    currentImageIndex = Math.floor((Math.random() * 15) + 0);
	
	//alert(currentImageIndex);

    if (currentImageIndex == index) {
        currentImageIndex = Math.floor((Math.random() * 15) + 0);;
    }

    var options = {
        duration: fadeSpeed,
        queue: false
    };
	//alert(currentImageIndex);
    $('#' + previousImageId).fadeOut(options);
    $('#' + imageIds[currentImageIndex]).fadeIn(options);
}
}