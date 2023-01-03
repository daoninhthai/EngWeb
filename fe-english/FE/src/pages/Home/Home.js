import React, { Component } from 'react'
import CategoryItem from '../../components/CategoryItem/CategoryItem';
import { Container, Col, Row } from 'reactstrap';
import Header from "../../components/Header/Header.js";
import './Home.css'
import Footer from '../../components/Footer/Footer';

import Slider from "react-slick";
class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            items: [{
                id: 1,
                src: "/grammar",
                name: "Grammar",
                description: "Your guide to English grammar. (Illustrated)"
            },
            {
                id: 2,
                src: "/vocabCategories",
                name: "Vocabulary",
                description: "Learn English vocabulary by topic. (Illustrated)"
            },
            {
                id: 3,
                src: "/q&a",
                name: "Q&A",
                description: "What do you really know about England, the UK and the English language?"
            },
            {
                id: 4,
                src: "/listening",
                name: "Listening",
                description: "Tricky English pronunciation. With listening practice."
            },
            {
                id: 5,
                src: "/test",
                name: "Test",
                description: "Have you really learnt what you think you have learnt?"
            },
            {
                id: 6,
                src: "/privatechat",
                name: "Chat",
                description: "We like to talk with you, and we like you to talk to each other. Practise your communication skills."
            }
            ]
        }
    }

    render() {
        let cards = this.state.items.map((item) => {
            return (
                <div className="Item" key={item.id}>
                    <CategoryItem item={item}></CategoryItem>
                </div>
            );
        })
  
        return (

            <div className="Home">
                <div className="Home_Header">
                    <Header></Header>
                </div>
                <div className="Content">
                    <div className="Dock_Notification">
                       
                    </div>


                    <div className='content-card'><div className="Content_Row">
                        {cards}
                    </div></div>
                    
                    <div className="Notice">    
                        <p>The most beautiful thing about learning is that no one take that away form you...</p>
                    </div>
                    <div className="Home_Footer">
                        <Footer></Footer>
                    </div>
                </div>
            </div>
        );
    }

}

export default Home;