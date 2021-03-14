import React from 'react';

// Styling
import '../styles/App.css';

// Routing
import { Link } from 'react-router-dom';
import { routingUtils } from '../utils';

function Home() {
  return (
    <div className="app-container">
      <header className="app-header">
        <Link to={routingUtils.createTestRoute()}>Test</Link>
        <Link to={routingUtils.createHomeRoute()}>Home</Link>
      </header>
      <h1>Welcome to your React App</h1>
    </div>
  );
}

export default Home;
