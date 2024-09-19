# 奇弈围棋

## API的使用

- UI ：java Swing

- 联机：java socket

## 难点：围棋相关规则的实现

- 建立棋串的气数组 -1棋盘初始状态 0无气点（无气，可能是禁入点或打劫） 数字 
- 提子 

- 悔棋 

- 打劫 

- 禁入点 （包括自杀点）

 ## 核心规则实现思路

建立围棋棋盘数组 用于记录落下子的坐标  -1黑 1白

```java
int[][] board=new int[PATH+1][PATH+1];
```

建立棋子的气数组，每一个坐标上的数值代表 -1棋盘初始状态 0无气点（无气，可能是禁入点或打劫） 数字 

```java
int[][] air_white=new int[PATH+1][PATH+1];
int[][] air_black=new int[PATH+1][PATH+1];
```

每次落子，使用DFS算法实现棋串气的计算，计算后存入各自的气数组

禁入点，劫点，阻止落子，给弹窗，若已存入list中，删除落子的最后一个，如果count和turn更新了恢复，标记落子失败

禁入点判断(自杀)

判断落子后是否能提子（对方棋串【当前落子位置的上下左右的棋串】有气变为0的状态），如果不能判断落子后当前棋子所在棋串的气是否为0

打劫点判断(全局同形)

每次落子成功后把当前棋盘状态转化成String存入List,当前落子时把落子后的棋盘状态转换成String判断List中是否已存在当前String.



落子成功后检测对方所有棋子是否在对方的气数组为0的地方，如果有就提掉（board）



## 基础模式

- 单机模式
- 联机模式

## 可选功能

- 导出SGF棋谱
- 登录
- 教学模式
- 聊天
- 定时器

## 项目结构

- draw 用于界面的绘制
  - MyFrame 绘制窗口
  - Mypanel 绘制棋盘，控制事件监听
  - Mypaint 存放绘制围棋相关的相关方法
- entity 实体类
  - Position 用于记录坐标
  - Stone用于存储棋子信息
- socket 用于实现网络通信

- util工具类
  - Calculator 用于各种坐标的计算
  - Config 用于存储程序所用到的常量

## 思考&文心一言

Q1：要实现围棋的提子逻辑，我的思路是创建一个存放List的二维数组air用于记录每个已棋子的气的每一个坐标,再用Set去重计算出相连棋串的气数（去重后List的长度），最后长度为0的从棋盘上移除，这样做好吗，有什么更简单效率更高的方式吗？

>在围棋中实现提子（吃子）逻辑时，您提到的使用二维数组加List加Set的方法确实可以工作，但这种方法可能在效率上不是最优的，尤其是在棋盘较大（如19x19）且棋局复杂时。这里有几个更常见且效率更高的方法来实现提子逻辑：
>
>### 1. 深度优先搜索（DFS）或广度优先搜索（BFS）
>
>这是处理围棋中棋串连接和计算气数最常用的方法。您可以为每个颜色的棋子定义一个起始点，然后使用DFS或BFS遍历所有相连的棋子，同时标记它们并计算它们的气数。
>
>- **DFS**: 递归地探索每个相连的棋子，直到没有更多的相连棋子为止。
>- **BFS**: 使用队列来迭代地探索每个相连的棋子。
>
>这种方法通常使用一个二维布尔数组（或类似的数据结构）来跟踪哪些位置已经被访问过，以避免重复计算。
>
>### 2. 使用并查集（Union-Find）
>
>并查集是一种用于处理一些不交集的合并及查询问题的数据结构。在围棋中，它可以帮助您快速找到所有属于同一棋串的棋子，并计算它们的气数。
>
>- **初始化**: 为棋盘上的每个位置（或仅为放有棋子的位置）分配一个唯一的标识符，并让每个标识符指向自己（表示它们各自独立）。
>- **合并**: 当发现两个棋子是相连的时，将它们所属的集合合并（即将一个集合的根节点指向另一个集合的根节点）。
>- **查询**: 要计算一个棋串的气数，您只需要找到该棋串的根节点，并遍历其所有成员，计算它们的气数（同时去重）。
>
>### 3. 位运算（对于较小的棋盘）
>
>对于非常小的棋盘，您可以使用位运算来优化空间和时间复杂度。然而，这种方法在围棋的标准19x19棋盘上并不实用，因为需要的位数太多。
>
>### 4. 高效的棋盘数据结构
>
>考虑使用更高效的数据结构来存储棋盘状态，例如使用整数数组而不是二维数组，或者使用压缩位图等。但是，这些方法在实现上可能更复杂，并且对于简单的提子逻辑来说可能不是必需的。
>
>### 5. 结合多种方法
>
>在实际应用中，您可能会结合使用上述方法中的一些。例如，您可以使用DFS或BFS来找到所有棋串，并使用并查集来优化查找和合并棋串的过程。
>
>### 结论
>
>对于围棋的提子逻辑，深度优先搜索或广度优先搜索通常是最简单且足够高效的方法。如果您需要处理更复杂的棋局或优化性能，可以考虑使用并查集或其他更高级的数据结构。不过，请注意，在大多数情况下，简单的DFS或BFS方法就已经足够了。

Q2：DFS算法和BFS算法的英语全称是什么，简述它们的主要思想？

>DFS算法和BFS算法是图论中两种重要的搜索算法，它们的英文全称和主要思想如下：
>
>### DFS算法
>
>**英文全称**：Depth-First Search，简称DFS。
>
>**主要思想**：
>
>* 深度优先搜索算法是一种用于遍历或搜索树或图的算法。它从起始顶点开始，选择某一路径深度试探查找目标顶点。
>* 当该路径上不存在目标顶点时，回溯到起始顶点继续选择另一条路径深度试探查找目标顶点，直到找到目标顶点或试探完所有顶点后回溯到起始顶点，完成搜索。
>* 由于DFS是以后进先出的方式遍历顶点，因此，可以使用栈（stack）来存储已经被搜索但相连顶点还未被搜索的顶点。
>* DFS的核心思想包括回溯和剪枝，即在搜索过程中，当发现当前路径不可行时，回溯到上一个节点并尝试其他可能的路径。
>
>### BFS算法
>
>**英文全称**：Breadth-First Search，简称BFS。
>
>**主要思想**：
>
>* 广度优先搜索算法是一种图形搜索算法，它从指定的起始节点开始，逐层地向外扩展搜索，直到找到目标节点或遍历完整个图。
>* BFS算法的基本思想是：先访问起始节点，然后依次访问起始节点的邻居节点，再依次访问邻居节点的邻居节点，以此类推，直到搜索到目标节点或者遍历完整个图。
>* BFS算法使用队列来辅助实现节点的遍历顺序，保证每一层的节点按顺序访问。
>* 相比DFS，BFS更适用于寻找最短路径等问题，因为它总是先访问离起始节点最近的节点。
>
>综上所述，DFS和BFS是两种在图论中广泛应用的搜索算法，它们的主要区别在于搜索的顺序和使用的数据结构不同。DFS使用栈进行深度优先搜索，适用于需要遍历所有可能路径的场景；而BFS使用队列进行广度优先搜索，适用于寻找最短路径等场景。

Q3:怎么使用深度或广度优先搜索计算围棋的气?

> 在围棋中，使用深度优先搜索（DFS）或广度优先搜索（BFS）来计算一个棋串的“气”（即该棋串中所有棋子未被对方棋子紧挨的空格数量）通常涉及两个主要步骤：首先，识别并遍历整个棋串；其次，对于棋串中的每个棋子，计算其气数。
>
> 以下是使用DFS来计算围棋中棋串气数的基本步骤：
>
> ### 1. 初始化
> - 创建一个棋盘表示，通常是一个二维数组，其中每个元素表示棋盘上的一个位置（可能是空、己方棋子、对方棋子）。
> - 创建一个与棋盘大小相同的二维布尔数组（或类似结构），用于标记哪些位置已经被访问过。
> - 创建一个变量来存储当前棋串的气数总和。
>
> ### 2. 定义DFS函数
> - 输入参数包括棋盘位置（x, y）、棋串的颜色（通常是己方或对方，但在这里我们只关心己方棋串）、已访问数组、以及气数总和（作为引用传递）。
> - 检查当前位置（x, y）是否在棋盘范围内、是否属于当前棋串的颜色、以及是否已被访问过。如果不满足这些条件，则返回。
> - 如果当前位置是空的（即不是棋子），则根据规则（通常是相邻的四个方向或八个方向，取决于围棋规则）检查它是否可以作为棋串的一个气。如果是，则增加气数总和。
> - 将当前位置标记为已访问。
> - 对当前位置的每个相邻位置递归调用DFS函数。
>
> ### 3. 遍历棋串
> - 选择棋盘上己方的一个棋子作为起点。
> - 调用DFS函数，以该棋子为起点开始遍历整个棋串。
>
> ### 4. 汇总结果
> - DFS函数遍历完整个棋串后，返回的气数总和就是该棋串的气数。
>
> ### 注意
> - 在计算气数时，你需要确保只计算那些未被对方棋子紧挨的空位。
> - DFS或BFS的选择取决于你的具体需求和偏好。DFS更易于实现递归逻辑，而BFS则可能更适合于某些需要按层次遍历的情况（尽管在围棋中计算气数时，这一点通常不那么重要）。
> - 在实际应用中，你可能还需要考虑其他因素，如“眼”（即形成两个或更多真眼的棋串不会被提走）和“劫”（一种特殊的战斗形式）。
>
> ### 示例伪代码（DFS）
>
> ```python
> def dfs(board, visited, x, y, color, total_liberty):
>     if not in_bounds(x, y) or board[x][y] != color or visited[x][y]:
>         return
>     
>     visited[x][y] = True
>     
>     # 假设只考虑上下左右四个方向
>     directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]
>     for dx, dy in directions:
>         nx, ny = x + dx, y + dy
>         if in_bounds(nx, ny) and board[nx][ny] == EMPTY:  # EMPTY 是表示空位的常量
>             total_liberty[0] += 1  # 注意：total_liberty 是作为列表传递的，以支持引用传递
>     
>     for dx, dy in directions:
>         nx, ny = x + dx, y + dy
>         dfs(board, visited, nx, ny, color, total_liberty)
> 
> def calculate_liberties(board, color):
>     visited = [[False] * len(board[0]) for _ in board]
>     total_liberty = [0]  # 使用列表来支持引用传递
>     
>     # 遍历棋盘上所有己方棋子，对每个棋串调用DFS
>     for i in range(len(board)):
>         for j in range(len(board[0])):
>             if board[i][j] == color and not visited[i][j]:
>                 dfs(board, visited, i, j, color, total_liberty)
>     
>     return total_liberty[0]
> ```
>
> 请注意，上述伪代码中的 `in_bounds` 函数用于检查位置 `(x, y)` 是否在棋盘范围内，`EMPTY` 是一个表示棋盘上空位的常量（你需要根据你的棋盘表示来定义它）。此外，`calculate_liberties` 函数假设棋盘和颜色已经以适当的方式定义，并且返回了整个棋盘上所有己方棋串的气数总和。如果你想要分别计算每个棋串的气数，你可能需要稍微修改这个函数来存储和返回每个棋串的气数。

Q4：禁入点和打劫如何实现？我的思路是把所有棋串以及里面被包围的点填满，在这些区域里寻找周围四周都有棋子的点，即为对方的无气点，无气点的四个角如果有三个及以上是己方棋子，那就是一个眼，在棋串非只有一口气（即被对方棋子包围的情况），这个点就是禁入点，这时还有一部分对方的无气点并非是被棋串包围，检测这个点周围的所有己方棋子是否只有一口气（对方的无气点），如果不是，那这个点就是禁入点。再来说打劫的情况，如果落入对方的无气点提子，被提掉的这个子刚好在己方的无气点，为了避免打劫，不能落在当前被提子的无气点（即己方的禁入点），在别处落子后恢复这个无气点为可入点，这个逻辑有问题吗，是否会影响效率，有没有更简单容易理解用代码实现的方法？

>您的思路在理解围棋中的禁入点（眼位）和打劫的基本规则上是正确的，但在实现上可能需要一些调整和优化以提高效率和准确性。以下是一些具体的建议：
>
>### 禁入点（眼位）的实现
>
>1. **定义眼位**：眼位通常定义为由至少两个交叉点组成的区域，其中每个交叉点至少有两个“真眼”（即，两个方向上的连续空格，这些空格不能通过对方的棋子被填满）。
>
>2. **检测眼位**：
>   - 遍历棋盘上的每个点。
>   - 对于每个点，检查其周围是否可能形成眼位（即，至少有两个方向上的连续空格）。
>   - 使用递归或深度优先搜索（DFS）来确认这些空格是否真正形成眼位（即，空格之间不能通过对方棋子相连）。
>
>3. **优化**：
>   - 只在棋型变化后（如落子后）重新计算眼位，而不是每次移动都重新计算整个棋盘。
>   - 使用缓存或标记已检查的点，避免重复计算。
>
>### 打劫的实现
>
>1. **定义打劫**：当一方试图提走对方棋子时，如果这会导致对方也立即能提走己方的一个或多个棋子（形成循环提子），则称为打劫。
>
>2. **检测打劫**：
>   - 在尝试提子之前，检查提子后是否会导致对方也能立即提子。
>   - 如果检测到打劫，则不允许立即提子，而是将提子点标记为“打劫点”，并等待对方在别处落子后再解决打劫。
>
>3. **处理打劫**：
>   - 当对方在别处落子后，重新评估打劫点，看是否可以安全地提子。
>   - 如果可以，则进行提子；如果仍然导致对方能提子，则继续等待。
>
>### 代码实现建议
>
>- 使用棋盘的数据结构（如二维数组）来存储棋子的位置。
>- 使用递归或迭代的方法来搜索眼位和检测打劫。
>- 使用位运算或哈希表来优化搜索过程。
>- 编写单元测试来验证眼位和打劫的检测逻辑。
>
>### 效率和易理解性
>
>- **效率**：通过缓存、标记已检查的点和优化搜索算法来提高效率。
>- **易理解性**：将复杂的逻辑分解为小的、可管理的函数，每个函数负责一个具体的任务（如检测眼位、检测打劫等）。
>
>希望这些建议能帮助您更好地实现围棋中的禁入点和打劫逻辑。

Q5:禁入点不一定是眼位，当一个点只有上下左右被己方棋子包围时，只要每个己方棋子的气都大于1,那么，这个点就是对方的禁入点

您说得对，我之前的回答中关于禁入点的描述确实不够准确。在围棋中，禁入点（也称为“劫材”或“劫点”）通常指的是一个点，当对方棋子落在这个点上时，会立即导致己方的一个或多个棋子处于无气状态，但这些无气的棋子由于眼位或其他原因不能直接被提走，从而可能引发打劫。

然而，您所描述的情况——当一个点只有上下左右被己方棋子包围，且每个己方棋子的气都大于1时，这个点被视为对方的禁入点——其实更接近于一种特殊情况下的“必吃”点，而不是通常意义上的劫点。不过，在围棋的实战中，这样的点也确实是对方需要谨慎考虑是否落子的地方，因为它可能导致己方棋子的气数减少，进而增加被吃的风险。

为了更准确地实现这一逻辑，并在代码中体现，您可以考虑以下步骤：

1. **检测被包围的点**：遍历棋盘，找到那些只有上下左右四个方向被己方棋子包围的点。

2. **检查周围棋子的气**：对于这些被包围的点，检查其周围每个己方棋子的气数是否都大于1。这通常意味着您需要编写一个辅助函数来计算棋子的气数。

3. **标记禁入点**：如果满足上述条件，则将这些点标记为对方的禁入点。这些点可能需要在后续的决策过程中给予特殊的关注。

4. **处理特殊情况**：如果检测到这些禁入点中的任何一个实际上构成了一个眼位（即，有两个或更多连续空格形成的真眼），则可能需要重新评估其是否真的是禁入点，因为眼位通常不会被提走。

5. **考虑打劫的可能性**：虽然这些点本身可能不是劫点，但它们仍然可能引发打劫，特别是当对方尝试通过吃掉周围的己方棋子来减少其气数时。因此，在决策过程中仍然需要考虑打劫的可能性。

6. **优化和测试**：优化您的代码以提高效率，并编写单元测试来验证禁入点和打劫逻辑的正确性。

请注意，围棋的复杂性使得任何算法都难以完美地模拟所有可能的棋局变化。因此，在实际应用中，您可能需要根据具体情况对算法进行调整和优化。
