import React, {useEffect, useState} from "react";
import LoadingBox from "./LoadingBox";
import "../styles/TradeHistory.css";

const TradeHistory = () => {
    const [loading, setLoading] = useState(true);
    const [tradehistory, setTradeHistory] = useState([]);
    const [searchOption, setSearchOption] = useState("전체");
    const [searchQuery, setSearchQuery] = useState("");


    useEffect(() => {
        const tradeHistoryData = [
            {
                id: 1,
                date: "2023-06-15",
                position: "매도",
                stockCode: "123456",
                stockName: "ABC주식회사",
                unitPrice: 1500,
                quantity: 8,
                amount: 12000,
            },
            {
                id: 2,
                date: "2023-07-02",
                position: "매수",
                stockCode: "654321",
                stockName: "XYZ주식회사",
                unitPrice: 2000,
                quantity: 5,
                amount: 10000,
            },
            {
                id: 3,
                date: "2023-05-20",
                position: "매도",
                stockCode: "789012",
                stockName: "DEF주식회사",
                unitPrice: 2500,
                quantity: 4,
                amount: 10000,
            },
            {
                id: 4,
                date: "2023-08-10",
                position: "매수",
                stockCode: "345678",
                stockName: "GHI주식회사",
                unitPrice: 1800,
                quantity: 6,
                amount: 10800,
            },
            {
                id: 5,
                date: "2023-07-25",
                position: "매도",
                stockCode: "901234",
                stockName: "JKL주식회사",
                unitPrice: 3000,
                quantity: 3,
                amount: 9000,
            },
            {
                id: 6,
                date: "2023-09-05",
                position: "매수",
                stockCode: "567890",
                stockName: "MNO주식회사",
                unitPrice: 2200,
                quantity: 7,
                amount: 15400,
            },
            {
                id: 7,
                date: "2023-07-18",
                position: "매도",
                stockCode: "432109",
                stockName: "PQR주식회사",
                unitPrice: 1750,
                quantity: 10,
                amount: 17500,
            },
            {
                id: 8,
                date: "2023-08-01",
                position: "매수",
                stockCode: "098765",
                stockName: "STU주식회사",
                unitPrice: 1900,
                quantity: 5,
                amount: 9500,
            },
            {
                id: 9,
                date: "2023-07-07",
                position: "매도",
                stockCode: "654321",
                stockName: "VWX주식회사",
                unitPrice: 2100,
                quantity: 3,
                amount: 6300,
            },
            {
                id: 10,
                date: "2023-08-20",
                position: "매수",
                stockCode: "123789",
                stockName: "YZA주식회사",
                unitPrice: 1750,
                quantity: 9,
                amount: 15750,
            },
            {
                id: 11,
                date: "2023-07-12",
                position: "매도",
                stockCode: "654987",
                stockName: "BCD주식회사",
                unitPrice: 1950,
                quantity: 6,
                amount: 11700,
            },
            {
                id: 12,
                date: "2023-08-05",
                position: "매수",
                stockCode: "321654",
                stockName: "EFG주식회사",
                unitPrice: 2400,
                quantity: 4,
                amount: 9600,
            },
            {
                id: 13,
                date: "2023-07-28",
                position: "매도",
                stockCode: "987654",
                stockName: "HIJ주식회사",
                unitPrice: 2600,
                quantity: 2,
                amount: 5200,
            },
            {
                id: 14,
                date: "2023-08-15",
                position: "매수",
                stockCode: "456321",
                stockName: "KLM주식회사",
                unitPrice: 1800,
                quantity: 8,
                amount: 14400,
            },
            {
                id: 15,
                date: "2023-07-22",
                position: "매도",
                stockCode: "987123",
                stockName: "NOP주식회사",
                unitPrice: 2200,
                quantity: 5,
                amount: 11000,
            },
            {
                id: 16,
                date: "2023-08-08",
                position: "매수",
                stockCode: "654123",
                stockName: "QRS주식회사",
                unitPrice: 1900,
                quantity: 6,
                amount: 11400,
            },
            {
                id: 17,
                date: "2023-07-30",
                position: "매도",
                stockCode: "987456",
                stockName: "TUV주식회사",
                unitPrice: 2100,
                quantity: 4,
                amount: 8400,
            },
            {
                id: 18,
                date: "2023-08-18",
                position: "매수",
                stockCode: "789654",
                stockName: "WXY주식회사",
                unitPrice: 1950,
                quantity: 7,
                amount: 13650,
            },
            {
                id: 19,
                date: "2023-07-05",
                position: "매도",
                stockCode: "654321",
                stockName: "ZAB주식회사",
                unitPrice: 2300,
                quantity: 3,
                amount: 6900,
            },
            {
                id: 20,
                date: "2023-08-22",
                position: "매수",
                stockCode: "123654",
                stockName: "BCD주식회사",
                unitPrice: 2050,
                quantity: 5,
                amount: 10250,
            },
            {
                id: 21,
                date: "2023-07-15",
                position: "매도",
                stockCode: "789321",
                stockName: "EFG주식회사",
                unitPrice: 2400,
                quantity: 4,
                amount: 9600,
            },
            {
                id: 22,
                date: "2023-08-12",
                position: "매수",
                stockCode: "321987",
                stockName: "HIJ주식회사",
                unitPrice: 2600,
                quantity: 2,
                amount: 5200,
            },
            {
                id: 23,
                date: "2023-07-10",
                position: "매도",
                stockCode: "987321",
                stockName: "KLM주식회사",
                unitPrice: 1800,
                quantity: 8,
                amount: 14400,
            },
            {
                id: 24,
                date: "2023-08-03",
                position: "매수",
                stockCode: "456789",
                stockName: "NOP주식회사",
                unitPrice: 2200,
                quantity: 5,
                amount: 11000,
            },
            {
                id: 25,
                date: "2023-07-26",
                position: "매도",
                stockCode: "987654",
                stockName: "QRS주식회사",
                unitPrice: 1900,
                quantity: 6,
                amount: 11400,
            },
            {
                id: 26,
                date: "2023-08-10",
                position: "매수",
                stockCode: "654987",
                stockName: "TUV주식회사",
                unitPrice: 2100,
                quantity: 4,
                amount: 8400,
            },
            {
                id: 27,
                date: "2023-07-18",
                position: "매도",
                stockCode: "987123",
                stockName: "WXY주식회사",
                unitPrice: 1950,
                quantity: 7,
                amount: 13650,
            },
            {
                id: 28,
                date: "2023-08-01",
                position: "매수",
                stockCode: "123456",
                stockName: "ZAB주식회사",
                unitPrice: 2300,
                quantity: 3,
                amount: 6900,
            },
            {
                id: 29,
                date: "2023-07-28",
                position: "매도",
                stockCode: "789012",
                stockName: "ABC주식회사",
                unitPrice: 1500,
                quantity: 8,
                amount: 12000,
            },
            {
                id: 30,
                date: "2023-08-15",
                position: "매수",
                stockCode: "654321",
                stockName: "DEF주식회사",
                unitPrice: 2500,
                quantity: 4,
                amount: 10000,
            },
        ];

        setTradeHistory(tradeHistoryData);
        setLoading(false);
    }, []);

    const handleOptionChange = (selectedOption) => {
        setSearchOption(selectedOption);
    };

    const handleSearch = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleSearchSubmit = (e) => {
        e.preventDefault();
        // Search logic can be added here
        console.log("검색어:", searchQuery);
        // Update search results logic can be added here
    };

    const filteredTradeHistory = tradehistory.filter((trade) => {
        if (searchOption === "전체") {
            return trade.stockName.includes(searchQuery);
        } else {
            return (
                trade.stockName.includes(searchQuery) && trade.position === searchOption
            );
        }
    });

    return (
        <div className="tradehistory-container">
            <div className="tradehistory-inner-container">
                <div className="trade-history">
                    거래내역
                    <div className="search-option">
                        <div className="search-date">
                            <button className="search-today">당일</button>
                            <button className="search-week">1주일</button>
                            <button className="search-month">1개월</button>
                            <button className="search-3months">3개월</button>
                            <button className="search-6months">6개월</button>
                            <button className="search-direct-input">직접입력</button>
                        </div>
                        <div className="search-position">
                            <select
                                value={searchOption}
                                onChange={(e) => handleOptionChange(e.target.value)}
                            >
                                <option value="전체">전체</option>
                                <option value="매도">매도</option>
                                <option value="매수">매수</option>
                            </select>
                        </div>
                        <div className="search-stock">
                            <form onSubmit={handleSearchSubmit}>
                                <input
                                    type="text"
                                    placeholder="종목명을 입력하세요"
                                    value={searchQuery}
                                    onChange={handleSearch}
                                />
                                <button type="submit">검색</button>
                            </form>
                        </div>
                    </div>
                    <div className="trade-view">
                        <div className="table-container">
                            <table className="trade-table">
                                <thead>
                                <tr>
                                    <th>거래일자</th>
                                    <th>포지션</th>
                                    <th>종목코드</th>
                                    <th>종목명</th>
                                    <th>거래단가</th>
                                    <th>거래수량</th>
                                    <th>거래금액</th>
                                </tr>
                                </thead>
                                <tbody>
                                {filteredTradeHistory.map((trade) => (
                                    <tr key={trade.id}>
                                        <td>{trade.date}</td>
                                        <td>{trade.position}</td>
                                        <td>{trade.stockCode}</td>
                                        <td>{trade.stockName}</td>
                                        <td>{trade.unitPrice}</td>
                                        <td>{trade.quantity}</td>
                                        <td>{trade.amount}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TradeHistory;
