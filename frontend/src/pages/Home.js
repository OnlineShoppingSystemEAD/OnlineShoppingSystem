import React, { useState } from 'react';
import Slider from 'react-slick';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../styles/carouselStyles.css'; // Ensure you import your CSS file here
import rightArrow from '../images/right-arrow.png';
import leftArrow from '../images/left-arrow.png';
import TileGrid from "../components/TileGrid";
import ProductOverview from "../components/ProductOverview";
import Carousel from "../components/Carousel";

const Home = () => {
    const carouselImages = [
        {
            src: "https://images.pexels.com/photos/432059/pexels-photo-432059.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            mainText: "Discover New Fashion Trends",
            subText: "Experience the Latest Styles Today",
            buttonText: "Shop Now",
        },
        {
            src: "https://images.pexels.com/photos/247204/pexels-photo-247204.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            mainText: "Exclusive Collection Launch",
            subText: "Join Us for the Latest Arrivals",
            buttonText: "Explore More",
        },
        {
            src: "https://images.pexels.com/photos/1182825/pexels-photo-1182825.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            mainText: "Shop the Latest Styles",
            subText: "Discover New Fashion Trends",
            buttonText: "Browse Now",
        },
    ];

    return (
        <div className="flex flex-col min-h-screen">
            <Header/>
            <main className="flex-grow">
                <Carousel images={carouselImages}/> {/* Pass the image array here */}
                <TileGrid/>
                <ProductOverview/>
            </main>
            <Footer/>
        </div>
    );
};

export default Home;
