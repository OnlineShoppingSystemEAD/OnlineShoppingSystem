import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import About from './pages/About';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/about" element={<About />} />
        {/* Other routes can be added here */}
      </Routes>
    </Router>
  );
}

export default App;
