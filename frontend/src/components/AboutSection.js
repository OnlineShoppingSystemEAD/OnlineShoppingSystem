import React from 'react';

const AboutSection = () => {
  return (
    <section className="py-12">
      <div className="container mx-auto">
        <h2 className="text-3xl font-bold mb-6">Our Story</h2>
        <p className="text-gray-600">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris consequat consequat enim, non auctor massa ultrices non. Morbi sed odio massa.
        </p>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-8">
          <div>
            <img src="/path/to/image1.jpg" alt="Story" />
          </div>
          <div>
            <p className="text-gray-600">
              Donec gravida lorem elit, quis condimentum ex semper sit amet. Fusce eget ligula magna. Aliquam erat volutpat. Donec ac iaculis lectus. 
            </p>
          </div>
        </div>
      </div>
    </section>
  );
};

export default AboutSection;
