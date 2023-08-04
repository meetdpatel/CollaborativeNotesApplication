import React, { useState } from 'react';
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useNavigate } from 'react-router-dom';
import { EyeFill, EyeSlashFill } from 'react-bootstrap-icons';
import './Authentication.css';
import Cookies from 'js-cookie';

const Authentication = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [userDetails, setUserDetails] = useState({
    name: '',
    email: '',
    password: ''
  });
  const { name, email, password } = userDetails;
  const navigate = useNavigate();

  const onInputChange = (e) => {
    setUserDetails({ ...userDetails, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!isLogin && (userDetails.name.length < 1 || userDetails.name.length > 20)) {
      alert("Username must be between 1 and 20 characters long");
    }
    else if (userDetails.password.length < 5 || userDetails.password.length > 20) {
      alert("Password must be between 5 and 20 characters long");
    }
    else if (userDetails.email.length < 7) {
      alert("Invalid email");
    }
    else {
      if(isLogin){
        checkUserFromServer(userDetails);
      } else {
        addUserToServer(userDetails);
      }
    }
  };

  const addUserToServer = (data) => {
    axios.post("http://localhost:8080/user", data)
      .then((response) => {
        console.log(response);
        alert("User Details Saved Successfully");
        navigate('/home');
      }, (error) => {
        console.log(error);
        alert("Operation failed");
      });
    setUserDetails({ name: '', email: '', password: '' });
  };

  const checkUserFromServer = (data) => {
    axios.post("http://localhost:8080/login", data)
        .then((response) => {
            console.log(response);
            if (response.status === 200) {
                Cookies.set('userEmail', response.data.email); // Set cookie
                navigate('/home');
            } else {
                alert("Incorrect email or password");
            }
        }, (error) => {
            if (error.response && error.response.status === 404) {
                alert("User not found");
            } else if (error.response && error.response.status === 401) {
                alert("Incorrect password");
            } else {
                console.log(error);
                alert("Login operation failed");
            }
        });
};




  return (
    <div className="auth-container">
      <h1>Collaborative Notes Hub</h1>
      <h2>{isLogin ? "Login" : "Signup"}</h2>
      <Form onSubmit={handleSubmit} className="auth-form">
        {!isLogin &&
          <Form.Group className="mb-3" controlId="formBasicUsername">
            <Form.Label>Username</Form.Label>
            <Form.Control name="name" type="text" placeholder="Enter User Name" value={name} onChange={onInputChange} />
          </Form.Group>
        }
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email</Form.Label>
          <Form.Control name="email" type="text" placeholder="Enter Email" value={email} onChange={onInputChange} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <div className="password-field">
            <Form.Control name="password" type={showPassword ? "text" : "password"} placeholder="Enter Password" value={password} onChange={onInputChange} />
            <div className="password-toggle" onClick={() => setShowPassword(!showPassword)}>
              {showPassword ? <EyeSlashFill /> : <EyeFill />}
            </div>
          </div>
        </Form.Group>
        <Button variant="primary" type="submit">{isLogin ? "Login" : "Signup"}</Button>
      </Form>
      <Button variant="link" onClick={() => setIsLogin(!isLogin)}>
        Switch to {isLogin ? "Signup" : "Login"}
      </Button>
    </div>
  );
};

export default Authentication;
