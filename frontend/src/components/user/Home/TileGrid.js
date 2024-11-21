import React from 'react';
import '../../../styles/tileGrid.css'; // This file will hold your CSS styles

const TileGrid = () => {
    const tiles = [
        { id: 1, title: 'Women', subtitle: 'New Trend', imageUrl: 'https://images.pexels.com/photos/413885/pexels-photo-413885.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
        { id: 2, title: 'Men', subtitle: 'New Trend', imageUrl: 'https://images.pexels.com/photos/450212/pexels-photo-450212.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
        { id: 3, title: 'Watches', subtitle: 'Spring 2018', imageUrl: 'https://images.pexels.com/photos/3153853/pexels-photo-3153853.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
        { id: 4, title: 'Bags', subtitle: 'Spring 2018', imageUrl: 'https://images.pexels.com/photos/22434775/pexels-photo-22434775/free-photo-of-leather-bags-on-white-background.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
        { id: 5, title: 'Accessories', subtitle: 'Spring 2018', imageUrl: 'https://images.pexels.com/photos/7170533/pexels-photo-7170533.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
    ];

    return (
        <div className="tile-grid">
            {tiles.map((tile) => (
                <div key={tile.id} className="tile">
                    <div className="tile-image-wrapper">
                        <img src={tile.imageUrl} alt={tile.title} className="tile-image" />
                    </div>
                    <div className="tile-text">
                        <h2>{tile.title}</h2>
                        <p>{tile.subtitle}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default TileGrid;
