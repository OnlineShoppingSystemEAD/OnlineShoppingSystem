import React from 'react';
import Header from '../../components/User/Header';
import Footer from '../../components/User/Footer';
import AboutSection from '../../components/User/About/AboutSection';

const About = () => {
    return (
        <div className="flex flex-col min-h-screen">
            <Header />
            <main className="flex-grow">
                <AboutSection />
            </main>
            <Footer />
        </div>
    );
};


export default About;
