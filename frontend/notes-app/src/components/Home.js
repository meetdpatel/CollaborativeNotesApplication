import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import './Home.css';

const Home = () => {
  const [userDetails, setUserDetails] = useState({
    name: '',
    email: '',
    password: ''
  });
  const [selectedFile, setSelectedFile] = useState(null);
  const [showEditForm, setShowEditForm] = useState(false);

  const navigate = useNavigate();


  const onInputChange = (e) => {
    setUserDetails({ ...userDetails, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleFileUpload = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('file', selectedFile);
    axios.post('http://localhost:8080/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(response => {
      console.log(response);
      alert("File uploaded successfully");
    })
    .catch(error => {
      console.log(error);
      alert("File upload failed");
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Call your API to update the user details here
    axios.post("http://localhost:8080/user", userDetails)
      .then((response) => {
        console.log(response);
        alert("User Details Updated Successfully");
      }, (error) => {
        console.log(error);
        alert("Update operation failed");
      });
  };

  const handleLogout = () => {
    // Call your API to logout the user here
    axios.post("http://localhost:8080/logout")
      .then((response) => {
        console.log(response);
        alert("Logout successful");
        navigate('/login');
      }, (error) => {
        console.log(error);
        alert("Logout operation failed");
      });
  };

  return (
    <div className="home-container">
      <h1>Welcome, {userDetails.name}</h1>
      <Button variant="secondary" onClick={() => setShowEditForm(!showEditForm)}>Edit Profile</Button>
      {showEditForm && (
        <Form onSubmit={handleSubmit} className="profile-form">
          <Form.Group className="mb-3" controlId="formBasicUsername">
            <Form.Label>Username</Form.Label>
            <Form.Control name="name" type="text" placeholder="Enter User Name" value={userDetails.name} onChange={onInputChange} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control name="email" type="text" placeholder="Enter Email" value={userDetails.email} onChange={onInputChange} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control name="password" type="password" placeholder="Enter Password" value={userDetails.password} onChange={onInputChange} />
          </Form.Group>
          <Button variant="primary" type="submit">Update Profile</Button>
        </Form>
      )}
      <div className="notes-section">
        <h2>Your Notes</h2>
        <Form onSubmit={handleFileUpload}>
          <Form.Group controlId="formFile" className="mb-3">
            <Form.Label>Upload PDF</Form.Label>
            <Form.Control type="file" onChange={handleFileChange} />
          </Form.Group>
          <Button variant="primary" type="submit">Upload</Button>
        </Form>
      </div>
      <Button variant="secondary" onClick={handleLogout}>Logout</Button>
    </div>
  );
};

export default Home;
