import React from 'react'

const Footer = () => {
    return (
        <footer className="bg-gray-800 text-white py-8">
      <div className="container mx-auto grid grid-cols-1 md:grid-cols-4 gap-8">
        <div>
          <h6 className="font-bold">CATEGORIES</h6>
          <ul>
            <li>Women</li>
            <li>Men</li>
            <li>Shoes</li>
            <li>Watches</li>
          </ul>
        </div>
        <div>
          <h6 className="font-bold">HELP</h6>
          <ul>
            <li>Track Order</li>
            <li>Returns</li>
            <li>Shipping</li>
            <li>FAQs</li>
          </ul>
        </div>
        <div>
          <h6 className="font-bold">GET IN TOUCH</h6>
          <p>Any questions? Let us know at 379 Hudson St, New York, or call (+1) 96 716 6879.</p>
        </div>
        <div>
          <h6 className="font-bold">NEWSLETTER</h6>
          <form>
            <input className="p-2 w-full" type="email" placeholder="email@example.com" />
            <button className="bg-purple-500 px-4 py-2 mt-2 w-full">SUBSCRIBE</button>
          </form>
        </div>
      </div>
    </footer>
    )
}
export default Footer;