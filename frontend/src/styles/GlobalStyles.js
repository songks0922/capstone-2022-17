import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyles = createGlobalStyle`
  ${reset}
  * {
    box-sizing: border-box;
  }
  a {
    color: inherit;
    text-decoration: none;
  }
  input,button {
    background-color: transparent;
    border: none;
    outline: none;
  }
  ol,ul,li {
    list-style: none;
  }
  img {
    display: block;
    width: 100%;
    height: 100%;
  }
  button {
    cursor: pointer;
  }
  body {
    background-color: #fff;
    font-family: 'Pr-Regular';
    font-size: 0.9rem;
  }
  input[type="number"]::-webkit-outer-spin-button,
  input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
  
`;

export default GlobalStyles;