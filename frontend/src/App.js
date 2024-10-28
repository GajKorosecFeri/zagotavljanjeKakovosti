import React from 'react';
import './App.css';
import SeznamOpravil from './components/OpravilaComponent'; //path

const App = () => {
  return (
      <div className="App">
        <SeznamOpravil /> {/* Render the TaskList component */}
      </div>
  );
};

export default App;
