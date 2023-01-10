import React, {Component, useState} from 'react';
import './EnglishWordItem.css';
import { withRouter } from 'react-router-dom';
import speaker from '../../resources/speaker.svg';
import SpeechRecognition from './SpeechRecognition.js';
import Popup from 'reactjs-popup';


class EnglishWordItem extends Component {
    constructor(props) {
        super(props);
        this.url = this.props.item.spellingAudioURL;
        this.audio = this.props.audio;
        this.isPlayingAudio = false;
    }

    render() {
   
        const item = this.props.item;
        return (
          
            <div className="EnglishWordItem">
                <div className="LeftSide">
                    <div className="Name">
                        {item.content}
                    </div>
                    <div className="Pronunciation_Sound">
                        <div className="Pronunciation">
                            {item.spelling}
                        </div>
                        <button className="Sound" onClick={this.handlePlaySound.bind(this)}>
                        </button>
                        <Popup trigger={<div
          className="microphone-icon-container"
                            
        >
          <img src="https://www.iconpacks.net/icons/1/free-microphone-icon-342-thumb.png" className="microphone-icon" />
        </div>} position="right center" className='popup'>
                        <div> <SpeechRecognition dataFromParent={item.content}/></div>
                        </Popup>
                        
                    </div>
                </div>
                <div className="RightSide">
                    {item.description}
                </div>
                

                
            </div>
        )
    }
    handlePlaySound(){
        this.audio.pause();
        if(this.audio.src == this.props.item.spellingAudioURL){
            if(this.audio.currentTime==0){
                this.audio.play();
            }
            else {
                this.audio.pause();
                this.audio.currentTime=0;
            }
            return;
        }
        this.audio.currentTime = 0;
        this.audio.src = this.url;
        this.audio.load();
        this.audio.play();
        this.isPlayingAudio = true;
    }

}

export default withRouter(EnglishWordItem);