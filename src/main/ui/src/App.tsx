import React from 'react';
import {BrowserRouter} from 'react-router-dom'
import './App.css';
import Main  from './components/Main'

function App() {
  return (
    <BrowserRouter>
      <div className="App">
          <Main />
      </div>
    </BrowserRouter>
  );
}

export default App
