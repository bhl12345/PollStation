/**
 * 
 */
import React, { Component } from 'react'
import Logo from "../images/pontificate.png"
import { Link } from 'react-router';

export default class Sidebar extends Component {
  render() {
    return (
      <div>
        <div>
          <nav href="#navbar"   data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"><i /></nav>
          <aside>
            <div className="text-center">
            <img src={Logo} alt="Logo" />
            <br/>
            <br/>
              <span className="email"><i></i>bhl12345@pontificating-peasant.com</span>
            </div>
            <br/>
            <nav >
              <div>
              <ul>
              <a style={{     	    
            	    color: "rebeccapurple",  fontSize: "20px",     
            	    }}href= "https://www.pontificating-peasant.com/">The Pontificating Peasant</a>
              <br/>
              <br/>
              <h3>Resources</h3>
              <a style={{     	    
          	    color: "rebeccapurple", fontSize: "20px",      
      	    }} href="https://usafacts.org/">USAFacts</a>
              <br/>
              <a style={{     	    
          	    color: "rebeccapurple", fontSize: "20px",   
      	    }} href="https://www.snopes.com/">Check The Facts</a>
              </ul>
              </div>
            </nav>
          </aside>
        </div>
      </div>
    )
  }
}