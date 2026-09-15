const startButton = document.querySelector("#startButton");
const stopButton = document.querySelector("#stopButton");
const statusText = document.querySelector("#status");
const result = document.querySelector("#result");

let mediaRecorder;
let mediaStream;
let audioChunks = [];

startButton.addEventListener("click", startRecording);
stopButton.addEventListener("click", stopRecording);

async function startRecording() {
	try {
	        mediaStream = await navigator.mediaDevices.getUserMedia({audio: true});
			
			const options = MediaRecorder.isTypeSupported("audio/webm;codecs=opus") ? {  mimeType: "audio/webm;codecs=opus"} : undefined;
			
			mediaRecorder = new MediaRecorder(mediaStream, options);
			
			audioChunks = [];
			
			mediaRecorder.addEventListener("dataavailable", event => {if (event.data.size > 0) {audioChunks.push(event.data);}});
			
			mediaRecorder.addEventListener("stop", uploadRecording);
			
			mediaRecorder.start();
			
			statusText.textContent = "Recording...";
			startButton.disabled = true;
			stopButton.disabled = false;
			result.textContent = "";

	    } catch (error) {
	        statusText.textContent = "Microphone access was not granted.";
	        console.error(error);
	    }
}

function stopRecording() {
	if (mediaRecorder?.state === "recording") {
		mediaRecorder.stop();
	}
	
	mediaStream?.getTracks().forEach(track => track.stop());
	
	statusText.textContent = "Transcribing...";
	stopButton.disabled = true;
}

async function uploadRecording() {
	const audioBlob = new Blob(audioChunks, {type: mediaRecorder.mimeType || "audio/webm"});
	
	const formData = new FormData();
	
	formData.append("file", audioBlob, "recording.webm");
	
	try{
		const response = await fetch("/api/v1/transcription", {method: "POST", body: formData});
		
		if (!response.ok){
			throw new Error(`Request failed: ${reponse.status}`);
		}
		
		const data = await response.json();
		
		result.textContent = data.text;
		statusText.textContent = "Transcribe complete";
	} catch (error) {
		result.textContent = "The recording could not be transcribed."
		statusText.textContent = "Transcribe failed";
		console.error(error);
	} finally {
		audioChunks = [];
		startButton.disabled = false;
		stopButton.disabled = true;
	}
}