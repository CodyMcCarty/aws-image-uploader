import React, { useState, useEffect, useCallback } from "react";
import logo from "./logo.svg";
import "./App.css";
import axios from "axios";
import { useDropzone } from "react-dropzone";

const UserProfiles = () => {
  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then((res) => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        {/* {userProfile.userProfileId ? ( */}
        <img
          src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`}
          alt="You haven't uploaded yet"
        />
        {/* ) : (
          <br />
        )} */}
        <h1>{userProfile.username}</h1>
        <p>{userProfile.userProfileId} </p>
        <MyDropzone {...userProfile} />
        <br />
      </div>
    );
  });
};

function MyDropzone({ userProfileId }) {
  const onDrop = useCallback((acceptedFiles) => {
    console.log(acceptedFiles[0]);

    const formData = new FormData();
    formData.append("file", acceptedFiles[0]);

    axios
      .post(
        `http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
        formData,
        {
          headers: { "Content-Type": "multipart/form-data" },
        }
      )
      .then(() => {
        console.log(userProfileId);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p>Drop the Image here ...</p>
      ) : (
        <p>Drag 'n' drop an Image here, or click to select an Image</p>
      )}
    </div>
  );
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;
