import React, {Component} from "react";
import {getTweets} from "../utils/APIUtils";
import {Container} from "react-bootstrap";
import styled from "styled-components";

const MainPageWrapper = styled.div`
{
    display: flex;
    flex-direction: column;
    padding: 16px;
    justify-content: center;
    overflow: auto;
}
`;

export default class MainPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tweets: []
        };

        getTweets({
            username: this.props.user.username
        }).then(response => {
            this.setState(() => {
                return {
                    tweets: response,
                }
            })
        })
    }

    componentDidMount() {
        this.interval = setInterval(() => {
            getTweets({
                username: this.props.user.username
            }).then(response => {
                this.setState(() => {
                    return {
                        tweets: response,
                    }
                })
            })
        }, 10000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {
        const tweets = this.state.tweets.map(tweet => {
            return <Tweet tweet={tweet}/>
        });

        return (
            <MainPageWrapper>
                {tweets}
            </MainPageWrapper>
        )
    }
}

const Wrapper = styled.div`
{
    display: flex;
    justify-content: space-between;
}
`;

const ContainerWrapper = styled.div`
{
    border-top: solid 1px grey;
    border-bottom: solid 1px grey;
    padding: 4px; 
    margin: 4px;
    background-color: #f8f9fa;
}


`;

class Tweet extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <ContainerWrapper>
                <Container fluid>
                    <Wrapper>
                        <div>
                            Nick
                            <h4>{this.props.tweet.nick}</h4>
                        </div>
                        <div>
                            Username
                            <h4>@{this.props.tweet.username}</h4>
                        </div>
                        <div>
                            Created at
                            <h4>{this.props.tweet.createdAt}</h4>
                        </div>
                    </Wrapper>
                    <div style={{display: "flex", justifyContent: "center", marginTop: "8px"}}>
                        <p>{this.props.tweet.text}</p>
                    </div>
                </Container>
            </ContainerWrapper>
        );
    }
}