import React ,{ useRef, useState } from "react";
import SpeechRecognition, { useSpeechRecognition } from "react-speech-recognition";
import "./App.css";

function App (props)  {
  const commands = [
    {
      command: "open *",
      callback: (website) => {
        window.open("http://" + website.split(" ").join(""));
      },
    },
    {
      command: "change background colour to *",
      callback: (color) => {
        document.body.style.background = color;
      },
    },
    {
      command: "reset",
      callback: () => {
        handleReset();
      },
    },
    ,
    {
      command: "reset background colour",
      callback: () => {
        document.body.style.background = `rgba(0, 0, 0, 0.8)`;
      },
    },
  ];
  const { transcript, resetTranscript } = useSpeechRecognition({ commands });
  const [isListening, setIsListening] = useState(false);
  const microphoneRef = useRef(null);
  if (!SpeechRecognition.browserSupportsSpeechRecognition()) {
    return (
      <div className="mircophone-container">
        Browser is not Support Speech Recognition.
      </div>
    );
  }
  const handleListing = () => {
    setIsListening(true);
    microphoneRef.current.classList.add("listening");
    SpeechRecognition.startListening({
      continuous: true,
    });
  };
  const stopHandle = () => {
    setIsListening(false);
    microphoneRef.current.classList.remove("listening");
    SpeechRecognition.stopListening();
    if(transcript===props.dataFromParent){
        document.getElementById("demo").innerHTML = "CORRECT";
    }
    else{
        document.getElementById("demo").innerHTML = "INCORRECT";
    }
  };
  const handleReset = () => {
    stopHandle();
    resetTranscript();
    document.getElementById("demo").innerHTML = "";
  };
  const handleChildToProp = () => {
    props.dataChildToParent('This data is coming from Child');
  };
  return (
    <div className="microphone-wrapper"> 
      <div className="mircophone-container">
        <div
          className="microphone-icon-container"
          ref={microphoneRef}
          onClick={handleListing}
        >
          <img src="https://www.iconpacks.net/icons/1/free-microphone-icon-342-thumb.png" className="microphone-icon" />
        </div>
        <div className="microphone-status">
          {isListening ? "Listening........." : ""}
          <p id="demo"></p>
        </div>
        {isListening && (
          <button className="microphone-stop btn" onClick={stopHandle}>
            check
          </button>
        )}
      </div>
      
      {transcript && (
        <div className="microphone-result-container">
          <div className="microphone-result-text">{transcript}</div>
          <button className="microphone-reset btn" onClick={handleReset}>
            <img src="https://cdn-icons-png.flaticon.com/512/2618/2618245.png"></img>
          </button>
        </div>
      )}
    </div>
  );
}
export default App;