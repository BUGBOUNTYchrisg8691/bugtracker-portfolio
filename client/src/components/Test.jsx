import React from 'react';

// Routing
import { Link } from 'react-router-dom';
import { routingUtils } from '../utils';

function Test() {
  return (
    <div>
      <header className="app-header">
        <Link to={routingUtils.createTestRoute()}>Test</Link>
        <Link to={routingUtils.createHomeRoute()}>Home</Link>
      </header>
      <h1>This is a test</h1>
    </div>
  );
}

export default Test;
