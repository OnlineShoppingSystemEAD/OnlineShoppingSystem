import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import ContactSection from "../../components/ContactSection";

const Contact = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Header />
      <main className="flex-grow">
        <ContactSection />
      </main>
      <Footer />
    </div>
  );
};

export default Contact;
