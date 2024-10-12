import React, { useState } from 'react';
import Slider from 'react-slick';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/carouselStyles.css'; // Ensure you import your CSS file here
import rightArrow from '../images/right-arrow.png';
import leftArrow from '../images/left-arrow.png';
import TileGrid from "../components/TileGrid";
import ProductOverview from "../components/ProductOverview";

const Home = () => {
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

    const imageStyle = {
        width: '100%',
        height: '100vh',
        objectFit: 'cover',
        objectPosition: 'center',
    };

    const mainTextStyle = {
        position: 'absolute',
        top: '40%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        color: '#fff',
        fontSize: '3em',
        fontWeight: 'bold',
        fontFamily: 'Montserrat, sans-serif',
        textShadow: '2px 2px 4px rgba(0,0,0,0.7)',
        zIndex: 1,
        animation: 'fadeInText 3s ease-in-out',
    };

    const subTextStyle = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        color: '#fff',
        fontSize: '1.5em',
        fontWeight: 'lighter',
        fontFamily: 'Poppins, sans-serif',
        textShadow: '1px 1px 3px rgba(0,0,0,0.5)',
        zIndex: 1,
        animation: 'zoomInText 4s ease-in-out 1s',
    };

    return (
        <div className="flex flex-col min-h-screen">
            <Header />
            <main className="flex-grow">
                <Slider {...settingsMain} asNavFor={nav2} ref={(slider1) => setNav1(slider1)}>
                    <div style={{position: 'relative'}}>
                        <img
                            src="https://images.pexels.com/photos/432059/pexels-photo-432059.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                            alt="Slide 1"
                            style={imageStyle}
                        />
                        <div style={mainTextStyle}>Discover New Fashion Trends</div>
                        <div style={subTextStyle}>Experience the Latest Styles Today</div>
                        <button className="button">Shop Now</button>
                        {/* Apply className here */}
                    </div>
                    <div style={{position: 'relative'}}>
                        <img
                            src="https://images.pexels.com/photos/247204/pexels-photo-247204.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                            alt="Slide 2"
                            style={imageStyle}
                        />
                        <div style={mainTextStyle}>Exclusive Collection Launch</div>
                        <div style={subTextStyle}>Join Us for the Latest Arrivals</div>
                        <button className="button">Explore More</button>
                        {/* Apply className here */}
                    </div>
                    <div style={{position: 'relative'}}>
                        <img
                            src="https://images.pexels.com/photos/1182825/pexels-photo-1182825.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                            alt="Slide 3"
                            style={imageStyle}
                        />
                        <div style={mainTextStyle}>Shop the Latest Styles</div>
                        <div style={subTextStyle}>Discover New Fashion Trends</div>
                        <button className="button">Browse Now</button>
                        {/* Apply className here */}
                    </div>
                </Slider>

                {/* Thumbnail Slider */}
                <Slider {...settingsThumbs} asNavFor={nav1} ref={(slider2) => setNav2(slider2)}>
                    <div>
                        <img
                            src="https://images.pexels.com/photos/432059/pexels-photo-432059.jpeg?auto=compress&cs=tinysrgb&w=150&h=100&dpr=1"
                            alt="Thumbnail 1"/>
                    </div>
                    <div>
                        <img
                            src="https://images.pexels.com/photos/247204/pexels-photo-247204.jpeg?auto=compress&cs=tinysrgb&w=150&h=100&dpr=1"
                            alt="Thumbnail 2"/>
                    </div>
                    <div>
                        <img
                            src="https://images.pexels.com/photos/1182825/pexels-photo-1182825.jpeg?auto=compress&cs=tinysrgb&w=150&h=100&dpr=1"
                            alt="Thumbnail 3"/>
                    </div>
                </Slider>
                <TileGrid/>
                <ProductOverview />
            </main>
            <Footer/>
        </div>
    );
};

export default Home;
