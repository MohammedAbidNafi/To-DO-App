package com.margsapp.todo.listtasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.Hold
import com.margsapp.todo.R
import com.margsapp.todo.addtasks.AddFragment
import com.margsapp.todo.models.Task
import android.text.format.DateFormat
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

const val DATE_FORMAT = "EEEE, MMM, dd, yyyy"

class ListFragment : Fragment() {

    interface Callbacks {
        fun onTaskSelect(view: View?, taskId: UUID)
    }

    private var callbacks: Callbacks? = null
    private lateinit var fabButton: FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var subTitle: TextView
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())

    private val taskListViewModel: ListFragmentViewModel by lazy {
        ViewModelProviders.of(this).get(ListFragmentViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        taskRecyclerView = view.findViewById(R.id.tasks_list)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

        subTitle = view.findViewById(R.id.subtitle)

        //TODO Move to callback into mainactivity?
        fabButton = view.findViewById(R.id.floating_action_button)
        fabButton.setOnClickListener {
            val fragment = AddFragment()
            parentFragmentManager
                .beginTransaction()
                .addSharedElement(it, "list_to_add")
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(bottomAppBar)

        bottomAppBar.setNavigationOnClickListener {
            val bottomSheet = NavigationBottomDialogFragment()
            bottomSheet.show(parentFragmentManager, "TAG")
        }

        taskListViewModel.taskList.observe(viewLifecycleOwner,
            Observer { tasks ->
            tasks?.let {
                updateList(tasks)
            }})


        taskListViewModel.taskListSize.observe(viewLifecycleOwner,
        Observer { taskCount ->
            updateOverview(taskCount)
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        setHasOptionsMenu(true)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateList(tasks: List<Task>) {
        adapter?.submitList(tasks)
    }

    private fun updateOverview(listSize: Int) {
        var overviewString = getString(R.string.overview, listSize.toString())
        if(listSize == 0){
            overviewString += " Nice Job!"
        }
        subTitle.setText(overviewString)
    }


    //TaskAdapter for RecyclerView
    private inner class TaskAdapter(var tasks: List<Task>): androidx.recyclerview.widget.ListAdapter<Task, TaskHolder>(TaskDiffCallback()){
        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = getItem(position)
            holder.onBind(task)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.task_list_item, parent, false)
            return TaskHolder(view)
        }
    }


    //TaskHolder for RecyclerView
    private inner class TaskHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var task: Task

        val taskTitleView: TextView = itemView.findViewById(R.id.task_title)
        val dateTextView: TextView = itemView.findViewById(R.id.task_date)
        val priorityTextView: TextView = itemView.findViewById(R.id.task_priority)
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.task_checkbox)

        init{
            itemView.setOnClickListener(this)
            taskCheckBox.setOnClickListener {
                taskListViewModel.deleteItem(task)
            }
        }

        fun onBind(task: Task){
            this.task = task
            taskTitleView.text = this.task.title
            dateTextView.text = DateFormat.format(DATE_FORMAT, this.task.dueDate)
//            priorityTextView.text = this.task.priorty
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                itemView.transitionName = task.id.toString()
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onTaskSelect(v, task.id)
        }

    }

    class TaskDiffCallback: DiffUtil.ItemCallback<Task>() {
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

    }

}
