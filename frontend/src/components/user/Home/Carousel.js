import React, { useState } from 'react';
import Slider from 'react-slick';
import rightArrow from '../../../assets/right-arrow.png';
import leftArrow from '../../../assets/left-arrow.png';
import '../../../styles/carouselStyles.css'; // Import your CSS

const Carousel = ({ images }) => {
    const [nav1, setNav1] = useState(null);
    const [nav2, setNav2] = useState(null);

    const NextArrow = (props) => {
        const { onClick } = props;
        return (
            <div className="nextArrow" onClick={onClick}>
                <img src={rightArrow} alt="Next" />
            </div>
        );
    };

    const PrevArrow = (props) => {
        const { onClick } = props;
        return (
            <div className="prevArrow" onClick={onClick}>
                <img src={leftArrow} alt="Previous" />
            </div>
        );
    };

    const settingsMain = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        fade: true,
        nextArrow: <NextArrow />,
        prevArrow: <PrevArrow />,
        asNavFor: nav2,
    };

    const settingsThumbs = {
        slidesToShow: 3,
        slidesToScroll: 1,
        asNavFor: nav1,
        focusOnSelect: true,
        arrows: false,
        centerMode: true,
        variableWidth: true,
    };

    return (
        <>
            {/* Main Slider */}
            <Slider {...settingsMain} asNavFor={nav2} ref={(slider1) => setNav1(slider1)}>
                {images.map((image, index) => (
                    <div key={index} className="slide">
                        <img src={image.src} alt={`Slide ${index + 1}`} className="carousel-image" />

                        {/* Main Text */}
                        {image.mainText && (
                            <div className="main-text effect-fadeUp">
                                {image.mainText}
                            </div>
                        )}

                        {/* Sub Text */}
                        {image.subText && (
                            <div className="sub-text effect-slideRight">
                                {image.subText}
                            </div>
                        )}

                        {/* Conditionally Render Button */}
                        {image.mainText && (
                            <button className="carousel-button effect-zoomIn">
                                {image.buttonText}
                            </button>
                        )}
                    </div>
                ))}
            </Slider>

            {/* Thumbnail Slider */}
            <Slider {...settingsThumbs} asNavFor={nav1} ref={(slider2) => setNav2(slider2)}>
                {images.map((image, index) => (
                    <div key={index} className="thumbnail">
                        <img src={image.src} alt={`Thumbnail ${index + 1}`} />
                    </div>
                ))}
            </Slider>
        </>
    );
};

export default Carousel;
