FROM node:20.10.0

WORKDIR /app

COPY package*.json /app/

RUN npm install

COPY . /app

EXPOSE 4200 49153

CMD ["npm", "start"]
