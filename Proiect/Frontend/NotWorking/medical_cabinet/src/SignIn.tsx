// SignIn.tsx
import React, { useState } from 'react';

const SignIn: React.FC = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const handleSignIn = async () => {
    try {
      const response = await fetch('YOUR_BACKEND_API_URL/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });

      const data = await response.json();
      console.log('Sign-in response:', data);

      // Handle success or display an error message to the user
    } catch (error) {
      console.error('Error signing in');
      // Handle error and display an error message to the user
    }
  };

  return (
    <div>
      <h2>Sign In</h2>
      <form>
        <label>Email:</label>
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />

        <label>Password:</label>
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />

        <button type="button" onClick={handleSignIn}>
          Sign In
        </button>
      </form>
    </div>
  );
};

export default SignIn;
