import React, { useState, useEffect } from 'react';
import '../../../styles/productOverview.css';
import Carousel from "../Home/Carousel";
import ProductService from '../../../api/services/ProductService'; // Assuming this is the service file
const carouselImages = [
    {
        src: "https://objectstorage.ap-mumbai-1.oraclecloud.com/n/softlogicbicloud/b/cdn/o/products/400-600/193664152--1--1672131753.jpeg",
        mainText: "",
        subText: "",
        buttonText: "",
    },
    {
        src: "https://objectstorage.ap-mumbai-1.oraclecloud.com/n/softlogicbicloud/b/cdn/o/products/400-600/193664152--2--1672131754.jpeg",
        mainText: "",
        subText: "",
        buttonText: "",
    },
    {
        src: "https://objectstorage.ap-mumbai-1.oraclecloud.com/n/softlogicbicloud/b/cdn/o/products/400-600/193664152--3--1672131757.jpeg",
        mainText: "",
        subText: "",
        buttonText: "",
    },
];
const ProductOverview = () => {
    const [products, setProducts] = useState([]); // Store fetched products
    const [categories, setCategories] = useState([]); // Store fetched categories
    const [selectedCategoryId, setSelectedCategoryId] = useState(null); // Selected category
    const [likedProducts, setLikedProducts] = useState([]); // Store liked product IDs
    const [selectedProduct, setSelectedProduct] = useState(null); // For Quick View
    const [isModalOpen, setIsModalOpen] = useState(false); // Modal state
    const [quantity, setQuantity] = useState(1);

    // Fetch products and categories
    useEffect(() => {
        const fetchData = async () => {
            try {
                // Fetch categories from localStorage
                const savedCategories = JSON.parse(localStorage.getItem('categoryList')) || [];
                setCategories(savedCategories);

                // Fetch products from the backend API
                const response = await ProductService.getItems(); // Call service function
                console.log("Fetched products data:", response.data);
                setProducts(response.data || []);
            } catch (error) {
                console.error('Failed to load products or categories:', error.message);
            }
        };

        fetchData();
    }, []);

    // Handle quantity increase and decrease
    const increaseQuantity = () => setQuantity(quantity + 1);
    const decreaseQuantity = () => {
        if (quantity > 1) setQuantity(quantity - 1);
    };

    // Filter the products based on the selected category ID
    const filteredProducts = selectedCategoryId
        ? products.filter(product => product.categoryId === selectedCategoryId)
        : products;

    // Toggle the like state for a product
    const toggleLike = (productId) => {
        if (likedProducts.includes(productId)) {
            setLikedProducts(likedProducts.filter(id => id !== productId)); // Remove from liked
        } else {
            setLikedProducts([...likedProducts, productId]); // Add to liked
        }
    };

    // Open Quick View Modal
    const openQuickView = (product) => {
        setSelectedProduct(product);
        setIsModalOpen(true);
    };

    // Close Quick View Modal
    const closeQuickView = () => {
        setSelectedProduct(null);
        setIsModalOpen(false);
    };

    return (
        <div className="product-overview">
            {/* Header Section */}
            <header className="product-header">
                <h1>PRODUCT OVERVIEW</h1>
                <div className="product-categories">
                    {/* All Products Button */}
                    <span
                        className={selectedCategoryId === null ? 'active' : ''}
                        onClick={() => setSelectedCategoryId(null)}
                    >
                        All Products
                    </span>
                    {/* Dynamically Render Category Buttons */}
                    {categories.map((category) => (
                        <span
                            key={category.id}
                            className={selectedCategoryId === category.id ? 'active' : ''}
                            onClick={() => setSelectedCategoryId(category.id)}
                        >
                            {category.name}
                        </span>
                    ))}
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
                                <img src={product.imageURL} alt={product.name} className="product-image" />
                                <button className="quick-view-btn" onClick={() => openQuickView(product)}>
                                    Quick View
                                </button>
                            </div>
                            <div className="product-info">
                                <h3>{product.name}</h3>
                                <p>Price: ${product.price}</p>
                                <div className="like-icon" onClick={() => toggleLike(product.id)}>
                                    <i className={likedProducts.includes(product.id) ? "fas fa-heart liked" : "far fa-heart"}></i>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No products found in this category.</p>
                )}
            </div>

            {/* Quick View Modal */}
            {isModalOpen && selectedProduct && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <span className="close-modal" onClick={closeQuickView}>&times;</span>
                        <div className="quick-view-container">
                            <div className="quick-view-image">
                                <Carousel images={carouselImages} />
                            </div>
                            <div className="quick-view-details">
                                <h2>{selectedProduct.name}</h2>
                                <p className="product-price">Price: ${selectedProduct.price}</p>
                                <p className="product-description">Some description about the product.</p>

                                {/* Quantity Selector and Add to Cart */}
                                <div className="quantity-add">
                                    <button onClick={decreaseQuantity}>-</button>
                                    <input type="number" value={quantity} readOnly />
                                    <button onClick={increaseQuantity}>+</button>
                                    <button className="add-cart-btn"> ADD TO CART</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ProductOverview;
