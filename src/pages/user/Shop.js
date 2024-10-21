import React from 'react';
import Header from '../../components/Header';
import Footer from '../../components/Footer';
import ProductOverview from "../../components/ProductOverview";

const About = () => {
    return (
        <div className="flex flex-col min-h-screen">
            <Header />
            <main className="flex-grow">
                <ProductOverview />
            </main>
            <Footer />
        </div>
    );
};

export default About;
