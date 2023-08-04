import { Routes, Route, BrowserRouter } from "react-router-dom";
import App from "./App";
import Authentication from "./components/Authentication";
import Home from "./components/Home";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={"/"} element={<App />} />
        <Route path={"/auth"} element={<Authentication />} />
        <Route path={"/home"} element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}
