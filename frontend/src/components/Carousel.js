import React, { useState } from 'react';
import Slider from 'react-slick';
import rightArrow from '../images/right-arrow.png';
import leftArrow from '../images/left-arrow.png';
import '../styles/carouselStyles.css'; // Import CSS for styling

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
                    <div key={index} style={{ position: 'relative' }}>
                        <img src={image.src} alt={`Slide ${index + 1}`} style={{ width: '100%', height: '100vh', objectFit: 'cover' }} />

                        {/* Main Text */}
                        {image.mainText && (
                            <div style={{ position: 'absolute', top: '40%', left: '50%', transform: 'translate(-50%, -50%)', color: '#fff', fontSize: '3em', fontWeight: 'bold', zIndex: 1 }}>
                                {image.mainText}
                            </div>
                        )}

                        {/* Sub Text */}
                        {image.subText && (
                            <div style={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', color: '#fff', fontSize: '1.5em', fontWeight: 'lighter', zIndex: 1 }}>
                                {image.subText}
                            </div>
                        )}

                        {/* Conditionally Render Button */}
                        {image.mainText && (
                            <button className="button" >
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
