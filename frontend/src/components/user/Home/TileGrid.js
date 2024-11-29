import React from 'react';

const TileGrid = () => {
  const tiles = [
    {
      id: 1,
      title: 'Women',
      subtitle: 'New Trend',
      imageUrl:
        'https://images.pexels.com/photos/413885/pexels-photo-413885.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    },
    {
      id: 2,
      title: 'Men',
      subtitle: 'New Trend',
      imageUrl:
        'https://images.pexels.com/photos/450212/pexels-photo-450212.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    },
    {
      id: 3,
      title: 'Watches',
      subtitle: 'Spring 2018',
      imageUrl:
        'https://images.pexels.com/photos/3153853/pexels-photo-3153853.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    },
    {
      id: 4,
      title: 'Bags',
      subtitle: 'Spring 2018',
      imageUrl:
        'https://images.pexels.com/photos/22434775/pexels-photo-22434775/free-photo-of-leather-bags-on-white-background.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    },
    {
      id: 5,
      title: 'Accessories',
      subtitle: 'Spring 2018',
      imageUrl:
        'https://images.pexels.com/photos/7170533/pexels-photo-7170533.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
    },
  ];

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 p-6">
      {tiles.map((tile) => (
        <div
          key={tile.id}
          className="relative overflow-hidden bg-gray-100 rounded-lg shadow-lg transition-transform duration-300 ease-in-out hover:-translate-y-2 hover:shadow-xl cursor-pointer"
        >
          {/* Image Container */}
          <div className="overflow-hidden rounded-t-lg h-64">
            <img
              src={tile.imageUrl}
              alt={tile.title}
              className="w-full h-full object-cover transition-transform duration-500 ease-in-out hover:scale-110"
            />
          </div>

          {/* Text Content */}
          <div className="p-4 bg-white">
            <h2 className="font-bold text-xl text-gray-800">{tile.title}</h2>
            <p className="text-gray-600">{tile.subtitle}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default TileGrid;
