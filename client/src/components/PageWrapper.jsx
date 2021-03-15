import React, { Children } from 'react';

function PageWrapper({ children, ...props }) {
  return (
    <div>
      <nav>Nav</nav>
      {children}
    </div>
  );
}

export default PageWrapper;
