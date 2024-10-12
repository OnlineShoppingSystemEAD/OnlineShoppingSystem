import React, { useState } from 'react';
import '../styles/productOverview.css';

const products = [
    { id: 1, title: 'Esprit Ruffle Shirt', price: '$16.64', category: 'Women', imageUrl: 'https://images.pexels.com/photos/6203797/pexels-photo-6203797.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1', new: true },
    { id: 2, title: 'Herschel Supply', price: '$35.31', category: 'Men', imageUrl: 'https://images.pexels.com/photos/15277901/pexels-photo-15277901/free-photo-of-man-wearing-a-yellow-jacket.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
    { id: 3, title: 'Only Check Trouser', price: '$25.50', category: 'Men', imageUrl: 'https://images.pexels.com/photos/15389752/pexels-photo-15389752/free-photo-of-portrait-of-a-man-wearing-sunglasses.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
    { id: 4, title: 'Classic Trench Coat', price: '$75.00', category: 'Women', imageUrl: 'https://images.pexels.com/photos/13816061/pexels-photo-13816061.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
    { id: 5, title: 'Leather Handbag', price: '$45.00', category: 'Bag', imageUrl: 'https://images.pexels.com/photos/904350/pexels-photo-904350.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
    { id: 6, title: 'Casual Shoes', price: '$60.00', category: 'Shoes', imageUrl: 'https://images.pexels.com/photos/13525839/pexels-photo-13525839.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
    { id: 7, title: 'Wristwatch', price: '$150.00', category: 'Watches', imageUrl: 'https://images.pexels.com/photos/17351225/pexels-photo-17351225/free-photo-of-luxury-watch-on-an-exhibition.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1' },
];

const ProductOverview = () => {
    const [selectedCategory, setSelectedCategory] = useState('All Products');

    // Filter the products based on the selected category
    const filteredProducts = selectedCategory === 'All Products'
        ? products
        : products.filter(product => product.category === selectedCategory);

    return (
        <div className="product-overview">
            {/* Header Section */}
            <header className="product-header">
                <h1>PRODUCT OVERVIEW</h1>
                <div className="product-categories">
                    <span
                        className={selectedCategory === 'All Products' ? 'active' : ''}
                        onClick={() => setSelectedCategory('All Products')}
                    >
                        All Products
                    </span>
                    <span
                        className={selectedCategory === 'Women' ? 'active' : ''}
                        onClick={() => setSelectedCategory('Women')}
                    >
                        Women
                    </span>
                    <span
                        className={selectedCategory === 'Men' ? 'active' : ''}
                        onClick={() => setSelectedCategory('Men')}
                    >
                        Men
                    </span>
                    <span
                        className={selectedCategory === 'Bag' ? 'active' : ''}
                        onClick={() => setSelectedCategory('Bag')}
                    >
                        Bag
                    </span>
                    <span
                        className={selectedCategory === 'Shoes' ? 'active' : ''}
                        onClick={() => setSelectedCategory('Shoes')}
                    >
                        Shoes
                    </span>
                    <span
                        className={selectedCategory === 'Watches' ? 'active' : ''}
                        onClick={() => setSelectedCategory('Watches')}
                    >
                        Watches
                    </span>
                </div>
                <div className="filter-search">
                    <button className="filter-btn">
                        <i className="fas fa-sliders-h"></i> Filter
                    </button>
                    <button className="search-btn">
                        <i className="fas fa-search"></i> Search
                    </button>
                </div>
            </header>

            {/* Product Grid */}
            <div className="product-grid">
                {filteredProducts.length > 0 ? (
                    filteredProducts.map(product => (
                        <div key={product.id} className="product-card">
                            <div className="product-image-wrapper">
                            {product.new && <span className="new-badge">New</span>}
                                <img src={product.imageUrl} alt={product.title} className="product-image" />
                            </div>
                            <div className="product-info">
                                <h3>{product.title}</h3>
                                <p>{product.price}</p>
                            </div>
                            <div className="wishlist-icon">
                                <i className="far fa-heart"></i> {/* FontAwesome icon */}
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No products found in this category.</p>
                )}
            </div>
        </div>
    );
};

export default ProductOverview;
