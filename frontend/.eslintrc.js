module.exports = {
  root: true,
  extends: [
    'standard',
    'plugin:vue/essential'
  ],
  parserOptions: {
    parser: 'babel-eslint',
    ecmaVersion: 2017,
    sourceType: 'module'
  },
  plugins: [
    'vue'
  ],
  env: {
    browser: true
  },
  rules: {
    // https://prettier.io/docs/en/options.html
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'space-before-function-paren': [2, {"anonymous": "never", "named": "never"}]
  }
}
